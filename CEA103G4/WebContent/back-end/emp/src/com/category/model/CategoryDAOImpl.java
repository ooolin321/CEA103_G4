package com.category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

public class CategoryDAOImpl implements CategoryDAO {
	private DataSource ds;

	public CategoryDAOImpl(DataSource ds) {
		this.ds = ds;
	}

	private static final String INSERT_STMT = "INSERT INTO CATEGORIES(CATEGORY_ID, CATEGORY_NAME, PARENT_CATEGORY_ID) VALUES (?, ?, ?)";
	private static final String FIND_BY_CATEGORY_ID_STMT = "SELECT * FROM CATEGORIES WHERE CATEGORY_ID = ?";
	private static final String FIND_BY_CATEGORY_NAME_STMT = "SELECT * FROM CATEGORIES WHERE CATEGORY_NAME = ?";
	private static final String FIND_BY_PARENT_CATEGORY_ID_STMT = "SELECT * FROM CATEGORIES WHERE PARENT_CATEGORY_ID = ?";
	private static final String FIND_BY_PARENT_CATEGORY_ID_STMT2 = "SELECT * FROM CATEGORIES WHERE PARENT_CATEGORY_ID IS NULL";
	private static final String FIND_ALL_STMT = "SELECT * FROM CATEGORIES ORDER BY PARENT_CATEGORY_ID, length(CATEGORY_NAME)";
	private static final String FIND_CUR_LV_MAX_CATEGORY_ID_STMT = "SELECT MAX(CATEGORY_ID) FROM CATEGORIES WHERE PARENT_CATEGORY_ID = ?";
	private static final String FIND_CUR_LV_MAX_CATEGORY_ID_STMT2 = "SELECT MAX(CATEGORY_ID) FROM CATEGORIES WHERE PARENT_CATEGORY_ID IS NULL";
	private static final String UPDATE_STMT = "UPDATE CATEGORIES SET CATEGORY_NAME = ?, PARENT_CATEGORY_ID =? WHERE CATEGORY_ID = ?";
	private static final String DELETE_STMT = "DELETE FROM CATEGORIES WHERE CATEGORY_ID = ?";
	private static final String GET_COUNT_STMT = "SELECT COUNT(1) FROM BOOKS WHERE CATEGORY_ID LIKE ? || '%'";

	@Override
	public void insert(Category category) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, category.getCategoryID());
			pstmt.setString(2, category.getCategoryName().replace(" ", ""));
			pstmt.setString(3, category.getParentCategoryID());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public Optional<Category> findByCategoryID(String categoryID) {
		Category category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_CATEGORY_ID_STMT);
			pstmt.setString(1, categoryID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				category = new Category();
				category.setCategoryID(rs.getString("CATEGORY_ID"));
				category.setCategoryName(rs.getString("CATEGORY_NAME"));
				category.setParentCategoryID(rs.getString("PARENT_CATEGORY_ID"));

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return Optional.ofNullable(category);
	}

	@Override
	public Optional<Category> findByCategoryName(String categoryName) {
		Category category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_CATEGORY_NAME_STMT);
			pstmt.setString(1, categoryName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				category = new Category();
				category.setCategoryID(rs.getString("CATEGORY_ID"));
				category.setCategoryName(rs.getString("CATEGORY_NAME"));
				category.setParentCategoryID(rs.getString("PARENT_CATEGORY_ID"));

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return Optional.ofNullable(category);
	}

	@Override
	public List<Category> findByParentCategoryID(String parentCategoryID) {
		List<Category> listCategory = new ArrayList<Category>();
		Category category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			if (parentCategoryID != null) {
				pstmt = con.prepareStatement(FIND_BY_PARENT_CATEGORY_ID_STMT);
				pstmt.setString(1, parentCategoryID);
			} else {
				pstmt = con.prepareStatement(FIND_BY_PARENT_CATEGORY_ID_STMT2);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				category = new Category();
				category.setCategoryID(rs.getString("CATEGORY_ID"));
				category.setCategoryName(rs.getString("CATEGORY_NAME"));
				category.setParentCategoryID(rs.getString("PARENT_CATEGORY_ID"));
				listCategory.add(category);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listCategory;
	}

	@Override
	public List<Category> getAll() {
		List<Category> listCategory = new ArrayList<Category>();
		Category category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				category = new Category();
				category.setCategoryID(rs.getString("CATEGORY_ID"));
				category.setCategoryName(rs.getString("CATEGORY_NAME"));
				category.setParentCategoryID(rs.getString("PARENT_CATEGORY_ID"));
				listCategory.add(category);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listCategory;
	}

	public Optional<Category> findCurrentLevelMaxCategoryID(String parentCategoryID, int categoryLevel) {
		Category category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Optional<Category> temp;
//		System.out.println("parentCategoryID - categoryLevel" );
//		System.out.println(parentCategoryID + " - " + categoryLevel);

		try {
			con = ds.getConnection();
			if (categoryLevel == 0) {
				pstmt = con.prepareStatement(FIND_CUR_LV_MAX_CATEGORY_ID_STMT2);
			} else {
				pstmt = con.prepareStatement(FIND_CUR_LV_MAX_CATEGORY_ID_STMT);
				pstmt.setString(1, parentCategoryID);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				temp = findByCategoryID(rs.getString(1));
				if (temp.isPresent()) {
					category = temp.get();
				}
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return Optional.ofNullable(category);
	}

	@Override
	public void update(Category category) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, category.getCategoryName());
			pstmt.setString(2, category.getParentCategoryID());
			pstmt.setString(3, category.getCategoryID());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String categoryID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, categoryID);

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<Integer> getBookNumByCategoryIDs(List<String> categoryIDs) {
		List<Integer> categoryCounts = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUNT_STMT);
			for (String categoryID : categoryIDs) {
				pstmt.setString(1, categoryID);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					categoryCounts.add(rs.getInt(1));
				}
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return categoryCounts;
	}

}
