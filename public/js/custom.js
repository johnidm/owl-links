$(document).ready(function(){  

  $(".button-collapse").sideNav(); 	
  $('.materialboxed').materialbox();
  $(".dropdown-button").dropdown();
   

  $('.modal-trigger').leanModal();
	
  
  $('.notify').click( function() {

	    $(this).fadeOut('slow', function(){
	        $(this).remove();
	    });
  });
  
  $('.trigger').click(function() {

	  	var id = $(this).attr('id-show');	  	

	  	if (id != undefined) {

	    	$('.modal-show-' + id).toggleClass('open');
	    	$('.page-show-' + id).toggleClass('blur');
	    	return false;
	    }

	  });
  
  setTimeout(showNotifications, 4000);
  function showNotifications(){
    
    
    $(".notify:first").fadeOut('slow', function(){
        $(this).remove();
    });

    if($(".notify").length > 0){
        setTimeout(showNotifications, 4000);
    }
  };
  
  $('#generate-api-key').click(function() {	  
	  
	  var num = Math.floor((Math.random() * 9) + 1);  
	  	  
	  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";	  
	  
	  var cifra = possible.charAt(Math.floor(Math.random() * possible.length));	
	 	  	  
	  var comp = "";
	  
	  var pos = possible.indexOf(cifra);
	  
	  for (var i = 1; i <= 4; i++) {
		  
		  var index = num * i + pos;
		  	  
		  
		  if (index >= possible.length) {
			  index = index - possible.length;
		  }
		  
		  var latter = 	possible.charAt(index);
		  
		  if (i % 2 == 0) {
			  comp += latter.toUpperCase();
			  
		  } else {
			  comp += latter.toLowerCase();
		  }
	  }
	  
	  var api_key = num + cifra + "-" + comp;
	  
	  $('#text-api-key').html(api_key);
	  
	
	  
  }); 
  
  
  
});


