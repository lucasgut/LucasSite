/* 
	Javascript functions 
*/

// From prototype.js @ www.conio.net | Returns an object reference to one or more strings
// ignore the fact that there are no arguments to this method -- javascript doesn't care how many you send (not strongly typed)
// The method checks the actual # of arguments -- returns a single object or an array
function $() {
    var elements = new Array();

    for (var i = 0; i < arguments.length; i++) {
        var element = arguments[i];

        if (typeof element == 'string')
            element = document.getElementById(element);

        if (arguments.length == 1)
            return element;

        elements.push(element);
    }

    return elements;
}


function deleteRecord(DownloadNum)
{
	formDownloads.DOWNLOAD_NUM.value = DownloadNum;
	formDownloads.DB_ACTION.value = "Delete";
	formDownloads.submit();
}

function toggleDiv(element, pathToFile) {
    var e = $(element);

    if (e) {
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