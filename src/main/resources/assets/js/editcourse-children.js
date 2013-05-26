$(function() {
    var availableTags = new Array(0);

    $.getJSON("/admin/courses",
      function(data) {
          $.each(data, function(i, item){
             availableTags.push(item.name)
          });
      });



    $( "#keyword" ).autocomplete({
      source: availableTags
    });
  });