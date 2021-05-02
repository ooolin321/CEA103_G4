package tools;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	// Singleton單例模式
	private static JedisPool pool = null;

	private JedisUtil() {
	}

	public static JedisPool getJedisPool() {
		// double lock
		if (pool == null) {
			synchronized (JedisUtil.class) {
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(20);
					config.setMaxIdle(20);
					config.setMaxWaitMillis(10000);
					pool = new JedisPool(config, "localhost", 6379);
				}
			}
		}
		return pool;
	}

	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}

	// 多執行緒關閉方法?
	public static void closeJedis(Jedis jedis) {
		try {
			if (jedis != null) {
				jedis.close();
			}
		} catch (Exception e) {
			closeBrokenResource(jedis);
		}
	}

	/**
	 * Return jedis connection to the pool, call different return methods depends on
	 * whether the connection is broken
	 */
	public static void closeBrokenResource(Jedis jedis) {
		try {
			pool.returnBrokenResource(jedis);
		} catch (Exception e) {
			destroyJedis(jedis);
		}
	}

	/**
	 * 在 Jedis Pool 以外強行銷毀 Jedis
	 */
	public static void destroyJedis(Jedis jedis) {
		if (jedis != null) {
			try {
				jedis.quit();
			} catch (Exception e) {
				System.out.println((">>> RedisUtil-jedis.quit() : " + e));
			}

			try {
				jedis.disconnect();
			} catch (Exception e) {
				System.out.println((">>> RedisUtil-jedis.quit() : " + e));
			}
		}
	}

}
