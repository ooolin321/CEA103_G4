const menu = [{
    "name": "首頁",
    "icon": "&#xe68e;",
    "url": "backendIndex.jsp",
    "hidden": false,
    "list": []
}, {
    "name": "前台管理",
    "icon": "&#xe653;",
    "url": "",
    "hidden": false,
    "list": [{
        "name": "會員管理",
        "url": "pages_component.html",
    }, {
        "name": "商品管理",
        "url": "pages_model.html"
    }, {
        "name": "訂單管理",
        "url": "pages_msg.html"
    }]
}, {
    "name": "後台管理",
    "icon": "&#xe612;",
    "url": "",
    "hidden": false,
    "list": [{
        "name": "員工列表",
        "url": "emp/listAllEmp.jsp"
    }, {
        "name": "新增員工",
        "url": "emp/addEmp.jsp"
    },{
        "name": "功能列表",
        "url": "fun/listAllFun.jsp"
    }]
}, {
    "name": "文章管理",
    "icon": "&#xe609;",
    "url": "",
    "hidden": false,
    "list": [{
        "name": "栏目管理",
        "url": "type_index.html"
    }, {
        "name": "文章管理",
        "url": "article_index.html"
    }]
}, {
    "name": "系统设置",
    "icon": "&#xe620;",
    "url": "",
    "hidden": false,
    "list": [{
        "name": "网站设置",
        "url": "web_index.html"
    }, {
        "name": "友情连接",
        "url": "flink_index.html"
    }, {
        "name": "导航管理",
        "url": "nav_index.html"
    }, {
        "name": "修改密码",
        "url": "web_pwd.html"
    }, {
        "name": "清除缓存",
        "url": "web_cache.html"
    }, {
        "name": "登录页",
        "url": "login.html"
    }]
}, {
    "name": "数据库",
    "icon": "&#xe857;",
    "url": "",
    "hidden": false,
    "list": [{
        "name": "备份数据库",
        "url": "db_backup.html"
    }, {
        "name": "还原数据库",
        "url": "db_reduction.html"
    }]
}, {
    "name": "退出登录",
    "icon": "&#xe65c;",
    "url": "out.html",
    "list": []
}, {
    "name": "开发者官网",
    "icon": "&#xe65f;",
    "url": "http://www.qadmin.net/",
    "target": "_blank",
    "list": []
}, {
    "name": "开发文档",
    "icon": "&#xe655;",
    "url": "http://docs.qadmin.net/",
    "target": "_blank",
    "list": []
}];

const config = {
    name: "ModeFemme",
    menu: menu,
};

// module.exports.name = "Qadmin";
// module.exports.menu = menu;
