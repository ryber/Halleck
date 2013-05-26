$(function() {
    var coursenames = new Array(0);

    $.getJSON("/admin/courses",
      function(data) {
          $.each(data, function(i, item){
             coursenames.push(item.name)
          });
      });



    $( "#keyword" ).autocomplete({
      source: coursenames
    });
  });