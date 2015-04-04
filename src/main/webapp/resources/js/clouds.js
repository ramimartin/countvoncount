function showClouds(words,mentions){
  $(".tag-title").css("visible","true");
  $("#words-cloud").jQCloud(words);
  $("#mentions-cloud").jQCloud(mentions);
}