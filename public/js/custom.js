$(document).ready(function(){  
	
  $('.materialboxed').materialbox();
  $(".dropdown-button").dropdown();
  $(".button-collapse").sideNav();
  
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
  }
  
  
  
});


