/*
 * Author: Joshi Dnyanesh Madhav
 * */

function onClick_l2r(l1, l2)
{
	var tNodes=document.getElementById("tNodes");
	for(var i=0; i<l1.options.length; i++)
		if(l1.options[i].selected)
		{
			var row=tNodes.insertRow(-1);
			var cell0=row.insertCell(0);
			var cell1=row.insertCell(1);
			var cell2=row.insertCell(2);
			var cell3=row.insertCell(3);
			cell0.innerHTML=l1.options[i].text;
			cell1.innerHTML="<input type='text' />";
			cell2.innerHTML="<input type='text' />";
			cell3.innerHTML="<input type='text' />";
			l2.options.add(l1.options[i--]);
		}
}

function onClick_r2l(l1, l2)
{
	var tNodes=document.getElementById("tNodes");
	for(var i=0; i<l2.options.length; i++)
		if(l2.options[i].selected)
		{
			tNodes.deleteRow(i);
			l1.options.add(l2.options[i--]);
		}
}

function onClick_moveUp(l2)
{
	var tNodes=document.getElementById("tNodes");
	for (var i = 0; i < l2.options.length; i++)
		if (i > 0 && l2.options[i].selected == true
				&& l2.options[i - 1].selected == false)
		{
			var row=tNodes.rows[i];
			tNodes.deleteRow(i);
			var row1=tNodes.insertRow(i-1);
			row1.innerHTML=row.innerHTML;
			row1.cells[1].childNodes[0].value=row.cells[1].childNodes[0].value;
			row1.cells[2].childNodes[0].value=row.cells[2].childNodes[0].value;
			row1.cells[3].childNodes[0].value=row.cells[3].childNodes[0].value;
			
			l2.add(l2.options[i], l2[i - 1]);
			l2.options[i - 1].selected = true;
			l2.options[i].selected = false;
		}
}

function onClick_moveDn(l2)
{
	var tNodes=document.getElementById("tNodes");
	for (var i = l2.options.length - 1; i >= 0; i--)
		if (i < l2.options.length - 1 && l2.options[i].selected == true
				&& l2.options[i + 1].selected == false)
		{
			var row=tNodes.rows[i];
			tNodes.deleteRow(i);
			var row1=tNodes.insertRow(i+1);
			row1.innerHTML=row.innerHTML;
			row1.cells[1].childNodes[0].value=row.cells[1].childNodes[0].value;
			row1.cells[2].childNodes[0].value=row.cells[2].childNodes[0].value;
			row1.cells[3].childNodes[0].value=row.cells[3].childNodes[0].value;
			
			l2.add(l2.options[i + 1], l2[i]);
			l2.options[i].selected = false;
			l2.options[i + 1].selected = true;
		}
}

function onClick_selectAll(l2)
{
	for(var i=0; i<l2.options.length; i++)
		l2.options[i].selected=true;
}

function onClick_t2s(text, l2)
{
	e=document.createElement("option");
	e.text=text;
	l2.add(e);
}

function onClick_del(l2)
{
	for(var i=l2.options.length-1; i>=0; i--)
		if(l2.options[i].selected)
			l2.remove(i);
}