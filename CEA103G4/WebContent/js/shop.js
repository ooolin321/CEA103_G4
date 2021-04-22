(function () {    
    const searchForm = document.getElementById('search');
    const searchInput = document.getElementById('search-input');
    
    const pagination = document.getElementById('pagination');

    const ITEM_PER_PAGE = 12;

    let paginationData = [];

    let nowPage = "1";
    // now data
    let nowData = [];


                getTotalPages(data); //計算頁數,分頁
                getPageData(1, data);
                nowData = data;
       

        productlist.addEventListener('click', (event) => {
      if (event.target.matches('.icon_heart_alt')) {
            // console.log(event.target.dataset.id) //每次設定事件時,可以先用console.log確認是否有抓到東西
            addFavoriteItem(event.target.dataset.id)
        }
    })

        searchForm.addEventListener('submit', event => {
        let results = []
        event.preventDefault()
        const regex = new RegExp(searchInput.value, 'i')
        results = data.filter(product => product.title.match(regex))
        console.log(results)
        displayDataList(results)
        getTotalPages(results)
        getPageData(1, results)
        nowData = results;
    })

        pagination.addEventListener('click', event => {
        if (event.target.tagName === 'A') {
            nowPage = event.target.dataset.page;
            getPageData(event.target.dataset.page)
        }
    })

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

        
    function addFavoriteItem(id) {
        //若使用者是第一次使用收藏功能，則 localStorage.getItem('favoriteProducts') 會找不到東西，所以需要建立一個空 Array。
        const list = JSON.parse(localStorage.getItem('favoriteProducts')) || []
        const movie = data.find(item => item.id === Number(id))
        //some 測試陣列中是否至少有一個元素,回傳布林值,相等的結果給item,if(some = true)
        if (list.some(item => item.id === Number(id))) {
            alert(`${product.product_name} 已經加到清單囉~`)
        } else {
            list.push(movie)
            alert(`已新增 ${product.product_name} 到收藏清單 `)
        }
        localStorage.setItem('favoriteProducts', JSON.stringify(list))
    }

})()