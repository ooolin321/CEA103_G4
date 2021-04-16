package com.product.model;
import java.util.List;
import java.util.Optional;


public class ProductService {
	
	private ProductDAO_interface dao;

	public ProductService() {
		dao = new ProductDAO();
	}

	public ProductVO addProduct(String product_name, String product_info, Integer product_price,
			Integer product_quantity, Integer product_remaining, Integer product_state, byte[] product_photo, String user_id,
			Integer pdtype_no, Integer start_price, Integer live_no) {

		ProductVO productVO = new ProductVO();
		
		productVO.setProduct_name(product_name);
		productVO.setProduct_info(product_info);
		productVO.setProduct_price(product_price);
		productVO.setProduct_quantity(product_quantity);
		productVO.setProduct_remaining(product_remaining);
		productVO.setProduct_state(product_state);
		productVO.setProduct_photo(product_photo);
		productVO.setUser_id(user_id);
		productVO.setPdtype_no(pdtype_no);
		productVO.setStart_price(start_price);
		productVO.setLive_no(live_no);
		dao.insert(productVO);

		return productVO;
	}

	public ProductVO updateProduct(Integer product_no, String product_name, String product_info, Integer product_price,
			Integer product_quantity, Integer product_remaining, Integer product_state, byte[] product_photo, String user_id,
			Integer pdtype_no, Integer start_price, Integer live_no) {

		ProductVO productVO = new ProductVO();

		productVO.setProduct_no(product_no);
		productVO.setProduct_name(product_name);
		productVO.setProduct_info(product_info);
		productVO.setProduct_price(product_price);
		productVO.setProduct_quantity(product_quantity);
		productVO.setProduct_remaining(product_remaining);
		productVO.setProduct_state(product_state);
		productVO.setProduct_photo(product_photo);
		productVO.setUser_id(user_id);
		productVO.setPdtype_no(pdtype_no);
		productVO.setStart_price(start_price);
		productVO.setLive_no(live_no);
		dao.update(productVO);

		return productVO;
	}

	public void deleteProduct(Integer product_no) {
		dao.delete(product_no);
	}

	public ProductVO getOneProduct(Integer product_no) {
		return dao.findByPrimaryKey(product_no);
	}

	public List<ProductVO> getAll() {
		return dao.getAll();
	}
	public Optional<ProductVO> getProductPic(Integer product_no){
		return dao.findProductPic(product_no);
	}
}




	

