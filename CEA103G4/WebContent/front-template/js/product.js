    
    const BASE_URL = 'https://movie-list.alphacamp.io';
    const INDEX_URL = BASE_URL + '/api/v1/movies/';
    const POSTER_URL = BASE_URL + '/posters/';
    
    
    const productdetails = document.querySelector(".product-details");
    
    
    productdetails.addEventListener('click', (event) => {
        if (event.target.matches('.icon_heart_alt')) {
            addFavoriteItem(event.target.dataset.id)
        }
    })

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