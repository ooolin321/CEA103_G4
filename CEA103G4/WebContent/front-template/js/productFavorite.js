(function () {

  const dataPanel = document.getElementById('data-panel')
  const data = JSON.parse(localStorage.getItem('favoriteProducts')) || []

  displayDataList(data)

    dataPanel.addEventListener('click', (event) => {
    if (event.target.matches('.btn-show-movie')) {
      showMovie(event.target.dataset.id)
    } else if (event.target.matches('.btn-remove-favorite')) {
      removeFavoriteItem(event.target.dataset.id)
    }
  })

  function displayDataList (data) {
    let htmlContent = ''
    data.forEach(function (item, index) {
      htmlContent += `
        <div class="col-lg-3 col-sm-6">
          <div class="card mb-2">
             <div class="product-item" >
      	    <div class="pi-pic">
      	    <div class="pi-img">
          <a href="${path}/product/product.do?product_no=${item.product_no}">
            <img class="card-img-top" src="${path}/ProductShowPhoto?product_no=${item.product_no}" alt=""></a>
            </div>
            <ul>
                        <li class="w-icon active">
                            <a href="#"><i class="icon_bag_alt"></i></a>
                        </li>   
                        <li class="w-heart" >
      	                            <i class="icon_heart_alt"  data-id="${item.product_no}"></i>
      	                        </li>
      	                    </ul>
      	                </div>
      	                <div class="pi-text">
      	                <a href="${path}/product/product.do?product_no=${item.product_no}">                
      	                        <h5>${item.product_name}</h5>    
      	                    <div class="product-price"><span>$</span>
      	                        ${item.product_price}
      	                    </div>
      	                </div></a>
            <div class="card-footer">
              <button class="btn btn-primary btn-show-movie" data-toggle="modal" data-target="#show-movie-modal" data-id="${item.id}">More</button>
              <button class="btn btn-danger btn-remove-favorite" data-id="${item.id}">X</button>
            </div>
          </div>
        </div>
      `
    })
    dataPanel.innerHTML = htmlContent
  }



function showMovie (id) {
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

//運用 id 值刪除電影
  function removeFavoriteItem (id) {
    // find movie by id
    //若 item.id === Number(id) 回傳 True，那麼 findIndex 則會回傳該項目的 index。若掃描了整個 Array 都沒有 Object 相符，則會回傳 -1。
    const index = data.findIndex(item => item.id === Number(id))
    if (index === -1) return //若無找到此筆資料 則停止此函式

    // remove movie and update localStorage
    data.splice(index, 1) // 找到此筆資料後，並將從資料庫中刪除
    //將data這組資料([{...}]) 從Object 轉換成  String 後，重新存回 LocalStorage
    localStorage.setItem('favoriteProducts', JSON.stringify(data))

    // repaint dataList
    //重新渲染 FAVORITE 這個頁面(此時的 data 已是最新的資料)
    displayDataList(data)
  }

})()