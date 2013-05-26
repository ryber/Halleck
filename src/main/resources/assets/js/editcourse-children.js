
var courses = {},
courseNames = new Array(0)


$.getJSON("/admin/courses",
  function(data) {
      $.each(data, function(i, item){
         courses[item.name] = item.id
         courseNames.push(item.name)
      })
  })



$( "#keyword" ).autocomplete({
  source: courseNames
})

$( "#searchform" ).submit(function(e) {
    $("#addcourseid")[0].value = courses[$("#keyword")[0].value]
})

