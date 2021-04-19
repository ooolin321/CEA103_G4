(function () {
    //這邊要改資料庫取出的json路徑
    const BASE_URL = 'https://movie-list.alphacamp.io';
    const INDEX_URL = BASE_URL + '/api/v1/movies/';
    const POSTER_URL = BASE_URL + '/posters/';

    const data = [];
    const productlist = document.getElementById('product-list');

    const searchForm = document.getElementById('search');
    const searchInput = document.getElementById('search-input');

    const pagination = document.getElementById('pagination');
    const ITEM_PER_PAGE = 12;
    let paginationData = [];

    // adding variables
    const modeSelector = document.querySelector("#mode-selector");
    // modeConditionCard or modeConditionList
    let modeCondition = "modeConditionCard";
    // now page
    let nowPage = "1";
    // now data
    let nowData = [];

    axios.get(INDEX_URL)
        .then((response) => {
            //迭代器 for-of 會把資料一個個取出放進item,再push進data陣列
            for (let item of response.data.results) {
                data.push(item);
                getTotalPages(data); //計算頁數,分頁
                getPageData(1, data);
                nowData = data;
            }
            displayDataList(data);
        }).catch((err) => console.log(err));


    // listen to data panel
    productlist.addEventListener('click', (event) => {
        if (event.target.matches('.btn-show-movie')) {
            showMovie(event.target.dataset.id);
        } else if (event.target.matches('.icon_heart_alt')) {
            // console.log(event.target.dataset.id) //每次設定事件時,可以先用console.log確認是否有抓到東西
            addFavoriteItem(event.target.dataset.id)
        }
    })
    //搜尋關鍵字屆時要改商品名稱
    // listen to search form submit event
    searchForm.addEventListener('submit', event => {
        let results = []
        event.preventDefault()
        const regex = new RegExp(searchInput.value, 'i')
        results = data.filter(movie => movie.title.match(regex))
        console.log(results)
        displayDataList(results)
        getTotalPages(results)
        getPageData(1, results)
        nowData = results;
    })

    //如果點擊到 a 標籤，則透過將頁碼傳入 getPageData 來切換分頁。
    // listen to pagination click event
    pagination.addEventListener('click', event => {
        if (event.target.tagName === 'A') {
            nowPage = event.target.dataset.page;
            getPageData(event.target.dataset.page)
        }
    })

    // listen to mode selector
    // modeSelector.addEventListener("click", function(e) {
    //     if (e.target.matches("#mode-card")) {
    //         modeCondition = "modeConditionCard";
    //         // 轉換時以目前頁數進行轉換，而不是回到第1頁
    //         getPageData(nowPage, nowData);
    //     } else if (e.target.matches("#mode-list")) {
    //         modeCondition = "modeConditionList";
    //         // 轉換時以目前頁數進行轉換，而不是回到第1頁
    //         getPageData(nowPage, nowData);
    //     }
    // });

    //動態商品頁
    //待改
    // 1.catagory-name改動態商品類別
    // 2.product-price改動態價格
    // 3.# 連結到應該有的位置

    function cardContent(data) {
        let htmlContent = ''
        data.forEach(function (item, index) {
            htmlContent += `
    <div class="col-lg-4 col-sm-6">
        <div class="card mb-2 productcard">
            <div class="product-item">
                <div class="pi-pic">
                <a href="#">
                    <img class="card-img-top" src="${POSTER_URL}${item.image}" alt=""></a>
                    <ul>
                        <li class="w-icon active">
                            <a href="#"><i class="icon_bag_alt"></i></a>
                        </li>   
                        <li class="w-heart" >
                            <i class="icon_heart_alt"  data-id="${item.id}"></i>
                        </li>
                    </ul>
                </div>
                <div class="pi-text">
                <a href="#">
                    <div class="catagory-name">Towel</div>                  
                        <h5>${item.title}</h5>    
                    <div class="product-price">
                        $14.00
                    </div>
                </div></a>
            </div>
        </div>
    </div>
      `
        })
        return htmlContent;
    }

    function getTotalPages(data) {
        let totalPages = Math.ceil(data.length / ITEM_PER_PAGE) || 1
        let pageItemContent = ''
        //註明這個 a 標籤會觸發 JavaScript 程式。
        for (let i = 0; i < totalPages; i++) {
            pageItemContent += `
        <li class="page-item">
          <a class="page-link" href="javascript:;" data-page="${i + 1}">${i + 1}</a>
        </li>
        `
        }
        pagination.innerHTML = pageItemContent;
    }

    function getPageData(pageNum, data) {
        paginationData = data || paginationData;
        let offset = (pageNum - 1) * ITEM_PER_PAGE;
        let pageData = paginationData.slice(offset, offset + ITEM_PER_PAGE);
        displayData(pageData);
    }

    function displayData(data) {
        let htmlContent = ''
        htmlContent = cardContent(data);
        productlist.innerHTML = htmlContent;
    }


    //撰寫新的 addFavoriteItem()，將使用者想收藏的電影送進 local storage 儲存起來
    function addFavoriteItem(id) {
        //若使用者是第一次使用收藏功能，則 localStorage.getItem('favoriteProducts') 會找不到東西，所以需要建立一個空 Array。
        const list = JSON.parse(localStorage.getItem('favoriteProducts')) || []
        const movie = data.find(item => item.id === Number(id))
        //some 測試陣列中是否至少有一個元素,回傳布林值,相等的結果給item,if(some = true)
        if (list.some(item => item.id === Number(id))) {
            alert(`${movie.title} 已經加到清單囉~`)
        } else {
            list.push(movie)
            alert(`已新增 ${movie.title} 到收藏清單 `)
        }
        localStorage.setItem('favoriteProducts', JSON.stringify(list))
    }

})()