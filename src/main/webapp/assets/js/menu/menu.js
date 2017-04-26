$().ready(function() {
	var menuLi = jQuery('#s-menu  li');//主菜单容器
	var sideBar = jQuery('#s-sidebar');//子菜单容器
	var curRef = window.sessionStorage["curRef"];//当前主菜单 data-ref 属性值
	var curLi = window.sessionStorage["curLi"];//当前子菜单 data-refby 属性值
	//显示子导航
	function showChildList(cur){
		    var ref = jQuery(cur).find('a').attr('data-ref');
				jQuery(cur).addClass('active').siblings().removeClass('active');
				jQuery(sideBar).find('#'+ref).show().siblings().hide();
			if(window.sessionStorage){
				window.sessionStorage["curRef"] = ref;
			}    		
	}
	 
	//页面初次加载	
	jQuery.each(menuLi,function(i,item){ 
		  if(!curRef && jQuery(item).hasClass('active')){
			  showChildList(item);
			  return false;//找到即跳出遍历
		  }    		  
		  else if(curRef && (jQuery(item).find('a').attr('data-ref') == curRef)){
			  	 showChildList(item);
	             return false;
         }   
	});
	
	if(curLi && curRef){
		jQuery(sideBar).find('li').removeClass('active');
		jQuery(sideBar).find('#'+curRef+' li[data-refby='+curLi+']').addClass('active');
	 }
		
	//点击主导航条
	jQuery(menuLi).click(function(){
		showChildList(this);		
	});
	
	//点击子导航
	jQuery(sideBar).find('a').click(function(){	
		jQuery(sideBar).find('li').removeClass('active');
		jQuery(this).parent().addClass('active');		
		window.sessionStorage["curLi"] = jQuery(this).parent().attr('data-refby');
	});
	
});
