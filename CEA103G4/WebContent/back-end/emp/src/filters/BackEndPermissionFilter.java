package filters;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.adminpermission.model.AdminPermissionVO;

public class BackEndPermissionFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();

		//判斷權限
		List<AdminPermissionVO> adminpermissionVO_s = (List<AdminPermissionVO>) session.getAttribute("adminpermissionVO_s");
	   
		List<String> list = new ArrayList<>();
	    for(int i = 0; i < adminpermissionVO_s.size(); i++) {
	    	list.add(adminpermissionVO_s.get(i).getPer_id());	
	    }	    
//	    System.out.println("list"+ list);
	    	    
	    String uri = req.getRequestURI();
//	    System.out.println("uri "+ uri);
	    
	    List<String> PER0001 = new ArrayList<>();
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/Index.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/AddBookPics.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/AddOneBook.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/BookManagement.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/BookUpdateOne.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/CategoryManagement.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/Index.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/UpdateBookPic.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_BookManagement/UpdateCategory.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_Common/BookSearchForm.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_Common/BookTableHead.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_Common/ErrorMsgs.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_Common/PromoDetailTableHead.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_Common/PromoForm.jsp");
	    PER0001.add(req.getContextPath()+"/back-end/jsp_Common/PromoInfo.jsp");
	    
	    List<String> PER0002 = new ArrayList<>();
	    PER0002.add(req.getContextPath()+"/back-end/order/cancel_order.jsp");
	    PER0002.add(req.getContextPath()+"/back-end/order/create_order.jsp");
	    PER0002.add(req.getContextPath()+"/back-end/order/listAll_order.jsp");
	    PER0002.add(req.getContextPath()+"/back-end/order/listOne_order.jsp");
	    PER0002.add(req.getContextPath()+"/back-end/order/select_order.jsp");
	    PER0002.add(req.getContextPath()+"/back-end/order/update_order.jsp");
	    
	    List<String> PER0003 = new ArrayList<>();
	    PER0003.add(req.getContextPath()+"/back-end/bookclub_report/listAllBookClub_Report.jsp");
	    
	    List<String> PER0004 = new ArrayList<>();
	    PER0004.add(req.getContextPath()+"/back-end/forum/forumBack_forums.jsp");
	    PER0004.add(req.getContextPath()+"/back-end/forum/forumBack_messages.jsp");
	    PER0004.add(req.getContextPath()+"/back-end/forum/reviewBack.jsp");
	    
	    List<String> PER0005 = new ArrayList<>();
	    PER0005.add(req.getContextPath()+"/back-end/cs/csindex.jsp");
	    PER0005.add(req.getContextPath()+"/back-end/cs/listAllCs.jsp");

	    List<String> PER0007 = new ArrayList<>();
	    PER0007.add(req.getContextPath()+"/back-end/member/listAllMem.jsp");
	    PER0007.add(req.getContextPath()+"/back-end/member/listMems.jsp");
	    PER0007.add(req.getContextPath()+"/back-end/member/update_mem.jsp");
	    
	    List<String> PER0008 = new ArrayList<>();
	    PER0008.add(req.getContextPath()+"/back-end/jsp_MarketingManagement/Index.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_MarketingManagement/ShowTimerLog.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_PromoManagement/AddPromo.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_PromoManagement/AddPromoDetails.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_PromoManagement/PromoManagement.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_PromoManagement/ShowPromoDetails.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_PromoManagement/UpdatePromo.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_PromoManagement/UpdatePromoDetail.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_RecommManagement/RecommManagement.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_RecommManagement/ShowRecommKeyDetails.jsp");
	    PER0008.add(req.getContextPath()+"/back-end/jsp_RecommManagement/UpdateRecommKey.jsp");	    
	    PER0008.add(req.getContextPath()+"/back-end/advertising/create_ad.jsp");
    
	    List<String> PER0009 = new ArrayList<>();
	    PER0009.add(req.getContextPath()+"/back-end/lecture/addLecture.jsp");
	    PER0009.add(req.getContextPath()+"/back-end/lecture/listAllLecture.jsp");
	    PER0009.add(req.getContextPath()+"/back-end/lecture/listOneLecture.jsp");
	    PER0009.add(req.getContextPath()+"/back-end/lecture/update_lecture.jsp");
	    
	    List<String> PER0010 = new ArrayList<>();
	    PER0010.add(req.getContextPath()+"/back-end/admins/listAllAdmins.jsp");
	    PER0010.add(req.getContextPath()+"/back-end/admins/addAdmins.jsp");
	    PER0010.add(req.getContextPath()+"/back-end/admins/update_admins.jsp");
	    
	    if(list.contains("PER0001") && PER0001.contains(uri)) {
	    	chain.doFilter(request, response);				    	
	    }else if (list.contains("PER0002") && PER0002.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if (list.contains("PER0003") && PER0003.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if (list.contains("PER0004") && PER0004.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if (list.contains("PER0005") && PER0005.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if (list.contains("PER0007") && PER0007.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if (list.contains("PER0008") && PER0008.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if (list.contains("PER0009") && PER0009.contains(uri)){
	    	chain.doFilter(request, response);	
	    }else if(list.contains("PER0010") && PER0010.contains(uri)) {
	    	chain.doFilter(request, response);				    	
	    }else {
			res.sendRedirect(req.getContextPath() + "/back-end/login/permissionInvalidate.jsp");			    	
	    }	    
	}
	
//志達求救
//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws ServletException, IOException {
//
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		// 取得 session
//		HttpSession session = req.getSession();
//		// 從 session 判斷此user是否登入過
//		Object account = session.getAttribute("account_s");
//		
//		List<AdminPermissionVO> adminpermissionVO_s =  (List<AdminPermissionVO>) session.getAttribute("adminpermissionVO_s");
// 		List<String> list = new ArrayList<>();
//	    for(int i =0;i< adminpermissionVO_s.size();i++) {
//	    list.add(adminpermissionVO_s.get(i).getPer_id());		
//}
//      System.out.println(list);
	
//		String uri = req.getRequestURI();
//		
//		PermissionDelimitVO permissiondelimitVO = new PermissionDelimitService().getOneUri(uri);
//		String per = permissiondelimitVO.getPer_id();
//		
//		System.out.println("uri"+uri);
//		System.out.println("permissiondelimitVO"+per);
//			
//		if (account == null) {
//			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/login/login.jsp");
//			return;
//		} 					
//		else {
//			if(per == null) {
//				chain.doFilter(request, response);
//			}
//			
//			if(list.contains(per)) {
//					chain.doFilter(request, response);			
//			}else {
//	session.setAttribute("location", req.getRequestURI());
//	res.sendRedirect(req.getContextPath() + "/back-end/login/login.jsp");
//	return;
//				}
//			
//			}
//		}
}