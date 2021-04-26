	function cardContent(data, path) {
      		console.log(data);
      		const obj  = JSON.parse(data);
      	  console.log(obj)
      		let htmlContent = "";
      	  
      	 obj["results"].forEach(function (item, index) {
      		 console.log(item)
      	    htmlContent += `
      	    <div class="col-lg-4 col-sm-6">
      	        <div class="card mb-2 productcard">
      	            <div class="product-item">
      	                <div class="pi-pic">
      	                <a href="#">
      	                    <img class="card-img-top" src="${path}/ProductShowPhoto?product_no=${item.product_no}" alt=""></a>
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
      	                <a href="#">
      	                    <div class="catagory-name">${item.pdtype_no}</div>                  
      	                        <h5>${item.product_name}</h5>    
      	                    <div class="product-price">
      	                        ${item.product_price}
      	                    </div>
      	                </div></a>
      	            </div>
      	        </div>
      	    </div>
      	      `;
      	  });
      	  return htmlContent;
      	}
    