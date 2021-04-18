        (function() {

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
                } else if (event.target.matches('.btn-add-favorite')) {
                    // console.log(event.target.dataset.id) //每次設定事件時,可以先用console.log確認是否有抓到東西
                    addFavoriteItem(event.target.dataset.id)
                }
            })

            // listen to search form submit event
            searchForm.addEventListener('submit', event => {
                let results = []
                event.preventDefault()
                const regex = new RegExp(searchInput.value, 'i')
                results = data.filter(movie => movie.title.match(regex))
                console.log(results)
                // displayDataList(results)
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
            modeSelector.addEventListener("click", function(e) {
                if (e.target.matches("#mode-card")) {
                    modeCondition = "modeConditionCard";
                    // 轉換時以目前頁數進行轉換，而不是回到第1頁
                    getPageData(nowPage, nowData);
                } else if (e.target.matches("#mode-list")) {
                    modeCondition = "modeConditionList";
                    // 轉換時以目前頁數進行轉換，而不是回到第1頁
                    getPageData(nowPage, nowData);
                }
            });

            function cardContent(data) {
                let htmlContent = ''
                data.forEach(function(item, index) {
                    htmlContent += `
<div class="col-lg-4 col-sm-6">
        <div class="card mb-2">
            <div class="product-item">
                <div class="pi-pic">
                    <img src="${POSTER_URL}${item.image}" alt="">
                    <div class="icon">
                        <i class="icon_heart_alt"></i>
                    </div>
                    <ul>
                        <li class="w-icon active">
                            <a href="#"><i class="icon_bag_alt"></i></a>
                        </li>
                        <li class="quick-view"><a href="#">+ Quick View</a></li>
                        <li class="w-icon">
                            <a href="#"><i class="fa fa-random"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="pi-text">
                    <div class="catagory-name">Towel</div>
                    <a href="#">
                        <h5>${item.title}</h5>
                    </a>
                    <div class="product-price">
                        $14.00
                        <span>$35.00</span>
                    </div>
                </div>
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
                if (modeCondition === "modeConditionCard") {
                    htmlContent = cardContent(data);
                } else if (modeCondition === "modeConditionList") {
                    htmlContent = listContent(data);
                }
                productlist.innerHTML = htmlContent;
            }

            function listContent(data) {
                let htmlContent = `<ul class="list-group px-3">`;
                data.forEach(function(item, index) {
                    htmlContent += `
        <li class="list-group-item d-flex flex-row justify-content-between">
            <div class="col-8">${item.title}</div>
            <div class="col-4">
              <button class="btn btn-primary btn-show-movie" data-toggle="modal" data-target="#show-movie-modal" data-id="${item.id}">More</button>
              <button class="btn btn-info btn-add-favorite" data-id="${item.id}">+</button>
            </div>
          </li>
        `
                })
                htmlContent += `</ul>`;
                return htmlContent;
            }

            function showMovie(id) {
                // get elements
                const modalTitle = document.getElementById('show-movie-title');
                const modalImage = document.getElementById('show-movie-image');
                const modalDate = document.getElementById('show-movie-date');
                const modalDescription = document.getElementById('show-movie-description');

                // set request url
                const url = INDEX_URL + id
                console.log(url)

                // send request to show api
                axios.get(url).then(response => {
                    const data = response.data.results
                    console.log(data)

                    // insert data into modal ui
                    modalTitle.textContent = data.title
                    modalImage.innerHTML = `<img src="${POSTER_URL}${data.image}" class="img-fluid" alt="Responsive image">`
                    modalDate.textContent = `release at : ${data.release_date}`
                    modalDescription.textContent = `${data.description}`
                })
            }

            //撰寫新的 addFavoriteItem()，將使用者想收藏的電影送進 local storage 儲存起來
            function addFavoriteItem(id) {
                //若使用者是第一次使用收藏功能，則 localStorage.getItem('favoriteMovies') 會找不到東西，所以需要建立一個空 Array。
                const list = JSON.parse(localStorage.getItem('favoriteMovies')) || []
                const movie = data.find(item => item.id === Number(id))
                //some 測試陣列中是否至少有一個元素,回傳布林值,相等的結果給item,if(some = true)
                if (list.some(item => item.id === Number(id))) {
                    alert(`${movie.title} is already in your favorite list.`)
                } else {
                    list.push(movie)
                    alert(`Added ${movie.title} to your favorite list!`)
                }
                localStorage.setItem('favoriteMovies', JSON.stringify(list))
            }

        })()