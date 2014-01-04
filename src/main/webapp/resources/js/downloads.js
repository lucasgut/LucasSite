/* 
	Javascript functions 
*/

window.$ = function(selector) {
	return document.querySelector(selector);
};
  
function deleteRecord(downloadId)
{
	$.ajax({
	       url: "/LucasSite/Downloads(" + downloadId + ").xml",
	       dataType: "json",
	       type: 'DELETE',
	       success: function () {
                console.log('Download [' + downloadId + '] has been deleted.');
	       },
           error: ErrorCallback
	});	
}

//Error handler
function ErrorCallback(error) {
    alert(error.message + ": " + error.statusCode + "[" + error.statusText + "]");
    $("#dialog-form").dialog("close");
}

function toggleDiv(element, pathToFile) {
    var e = document.getElementById(element);
    if(e) {
        e.style.display = ((e.style.display != 'block') ? 'block' : 'none');
    }
	loadContent(element, pathToFile);     
}


/***** Ajax functions *****/
var dynamicContent_ajaxObjects = new Array(); 
var jsCache = new Array();
var enableCache = true; 

function loadContent(divId, pathToFile)
{
	if(enableCache && jsCache[pathToFile]) {
		document.getElementById(divId).innerHTML = jsCache[pathToFile];
		return;
	}

	var ajaxIndex = dynamicContent_ajaxObjects.length;
	document.getElementById(divId).innerHTML = 'Loading content...';
	dynamicContent_ajaxObjects[ajaxIndex] = new sack();
	dynamicContent_ajaxObjects[ajaxIndex].requestFile = pathToFile;

	dynamicContent_ajaxObjects[ajaxIndex].onCompletion = function(){ showContent(divId, ajaxIndex, pathToFile); };  
	dynamicContent_ajaxObjects[ajaxIndex].runAJAX();  
}

function showContent(divId, ajaxIndex, pathToFile)
{
	document.getElementById(divId).innerHTML = dynamicContent_ajaxObjects[ajaxIndex].response;
	if(enableCache) {
		jsCache[pathToFile] = dynamicContent_ajaxObjects[ajaxIndex].response;
	}
	dynamicContent_ajaxObjects[ajaxIndex] = false;
}