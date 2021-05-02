<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <header class="app-header"><a class="app-header__logo" href="<%=request.getContextPath()%>/front-end/index.jsp">Mode Femme</a>
    <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
    <!-- Navbar Right Menu-->
    <ul class="app-nav">
      <li class="app-search">
        <input class="app-search__input" type="search" placeholder="Search">
        <button class="app-search__button"><i class="fa fa-search"></i></button>
      </li>
      <!--Notification Menu-->
      <li class="dropdown"><a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Show notifications"><i class="fa fa-bell-o fa-lg"></i></a>
        <ul class="app-notification dropdown-menu dropdown-menu-right">
          <li class="app-notification__title">You have 4 new notifications.</li>
          <div class="app-notification__content">
            <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-primary"></i><i class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
              <div>
                <p class="app-notification__message">Lisa sent you a mail</p>
                <p class="app-notification__meta">2 min ago</p>
              </div></a></li>
              <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-danger"></i><i class="fa fa-hdd-o fa-stack-1x fa-inverse"></i></span></span>
                <div>
                  <p class="app-notification__message">Mail server not working</p>
                  <p class="app-notification__meta">5 min ago</p>
                </div></a></li>
                <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-success"></i><i class="fa fa-money fa-stack-1x fa-inverse"></i></span></span>
                  <div>
                    <p class="app-notification__message">Transaction complete</p>
                    <p class="app-notification__meta">2 days ago</p>
                  </div></a></li>
                  <div class="app-notification__content">
                    <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-primary"></i><i class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
                      <div>
                        <p class="app-notification__message">Lisa sent you a mail</p>
                        <p class="app-notification__meta">2 min ago</p>
                      </div></a></li>
                      <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-danger"></i><i class="fa fa-hdd-o fa-stack-1x fa-inverse"></i></span></span>
                        <div>
                          <p class="app-notification__message">Mail server not working</p>
                          <p class="app-notification__meta">5 min ago</p>
                        </div></a></li>
                        <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-success"></i><i class="fa fa-money fa-stack-1x fa-inverse"></i></span></span>
                          <div>
                            <p class="app-notification__message">Transaction complete</p>
                            <p class="app-notification__meta">2 days ago</p>
                          </div></a></li>
                        </div>
                      </div>
                      <li class="app-notification__footer"><a href="#">See all notifications.</a></li>
                    </ul>
                  </li>
                  <!-- User Menu-->
                  <li class="dropdown"><a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Open Profile Menu"><i class="fa fa-user fa-lg"></i></a>
                    <ul class="dropdown-menu settings-menu dropdown-menu-right">
                      <li><a class="dropdown-item" href="page-user.html"><i class="fa fa-cog fa-lg"></i> Settings</a></li>
 <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/user/user.do?user_id=${userVO.user_id}&action=getOne_For_Display"><i class="fa fa-user fa-lg"></i> Profile</a></li>
                      <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/userLogin.jsp"><i class="fa fa-sign-out fa-lg"></i> Logout</a></li>
                    </ul>
                  </li>
                </ul>
              </header>
              <!-- Sidebar menu-->
              <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
              <aside class="app-sidebar">
                <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="https://s3.amazonaws.com/uifaces/faces/twitter/jsa/48.jpg" alt="User Image">
                  <div>
                    <p class="app-sidebar__user-designation">Welcome</p>
                    <p class="app-sidebar__user-name">${userVO.user_name}</p>
                  </div>
                </div>
                <ul class="app-menu">
                  <li><a class="app-menu__item" href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp"><i class="app-menu__icon fa fa-drivers-license-o"></i><span class="app-menu__label">會員首頁</span></a></li>
                  <li class="treeview"><a class="app-menu__item" href="<%=request.getContextPath()%>/front-end/user/user.do?user_id=${userVO.user_id}&action=getOne_For_Update" data-toggle="treeview"><i class="app-menu__icon fa fa-gear"></i><span class="app-menu__label">會員資料管理</span></a></li>                  
                   <li class="treeview"><a class="app-menu__item" href="<%=request.getContextPath()%>/front-end/seller/productList.jsp" data-toggle="treeview"><i class="app-menu__icon fa fa-shopping-bag"></i><span class="app-menu__label">商品管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="<%=request.getContextPath()%>/front-end/productMangement/productList.jsp"><i class="icon fa fa-archive"></i>我的商品</a></li>
                      <li><a class="treeview-item" href="<%=request.getContextPath()%>/front-end/productMangement/productAdd.jsp"><i class="icon fa fa-edit"></i>新增商品</a></li>
                    </ul>
                  </li>
                  
                  <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i class="app-menu__icon fa fa-edit"></i><span class="app-menu__label">直售訂單管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="form-components.html"><i class="icon fa fa-circle-o"></i> Form Components</a></li>
                      <li><a class="treeview-item" href="form-custom.html"><i class="icon fa fa-circle-o"></i> Custom Components</a></li>
                      <li><a class="treeview-item" href="form-samples.html"><i class="icon fa fa-circle-o"></i> Form Samples</a></li>
                      <li><a class="treeview-item" href="form-notifications.html"><i class="icon fa fa-circle-o"></i> Form Notifications</a></li>
                    </ul>
                  </li>
                  <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i class="app-menu__icon fa fa-th-list"></i><span class="app-menu__label">直播訂單管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="table-basic.html"><i class="icon fa fa-circle-o"></i>我的購買訂單</a></li>
                      <li><a class="treeview-item" href="table-basic.html"><i class="icon fa fa-circle-o"></i>我的販賣訂單</a></li>
                    </ul>
                  </li>
                  <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i class="app-menu__icon fa fa-file-text"></i><span class="app-menu__label">直播專案管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href='<%=request.getContextPath()%>/front-end/liveManagement/liveList.jsp'><i class="icon fa fa-circle-o"></i>我的直播專案</a></li>
                      <li>
                      <FORM id="add_live" METHOD="post" ACTION="<%=request.getContextPath()%>/live/live.do" style="margin-bottom: 0px;">
					     <input type="hidden" name="user_id"  value="${userVO.user_id}">
					     <input type="hidden" name="action"	value="insert">
                      <a class="treeview-item" href="#" onclick="document.getElementById('add_live').submit();"><i class="icon fa fa-circle-o"></i>新增直播專案</a>
                      </FORM>
<!--                       <li> -->
<%--                    	  <FORM id="update_live" METHOD="post" ACTION="<%=request.getContextPath()%>/live/live.do" style="margin-bottom: 0px;"> --%>
<%-- 					     <input type="hidden" name="user_id"  value="${userVO.user_id}"> --%>
<!-- 					     <input type="hidden" name="action"	value="getOne_For_Update"> -->
<!--                       <a class="treeview-item" href="#" onclick="document.getElementById('update_live').submit();"><i class="icon fa fa-circle-o"></i>修改直播專案</a> -->
<!--                       </FORM> -->
<!--                       </li> -->
                    </ul>
                  </li>
                </ul>
              </aside>
               