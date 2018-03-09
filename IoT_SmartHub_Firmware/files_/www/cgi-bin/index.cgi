#!/bin/ash


echo ""
echo "<html>"
echo "<head>"
echo '<link rel="icon" type="image/x-icon" href="../favicon.ico">'
echo "<title>Arexim Remote</title>"
echo "</head>"

echo '<body onload="window.resizeTo(480,800)">'
echo '<right><select class="select" dir="rtl" id="leaveCode" name="leaveCode" onchange="if (this.value)   window.location.href=x+this.value"><option value=1>1</option><option value=2>2</option><option value=3>3</option><option value=4>4</option><option value=5>5</option><option value=6>6</option><option value=7>7</option><option value=8>8</option><option value=9>9</option><option value=10>10</option><option value=11>11</option><option value=12>12</option><option value=13>13</option><option value=14>14</option><option value=15>15</option><option value=16>16</option><option value=17>17</option><option value=18>18</option><option value=19>19</option><option value=20>20</option></select></right>'
echo '<style>
 .select {position: inherit;float: right;min-width: 100px;font-size: 250%;color: green;display: inline-block;padding: 5px 5px 5px 5px;border: outset 4px green;  }</style>'

echo '<script type="text/javascript"> var x = "set.sh?namex=";function setSelectValue (id, val){document.getElementById(id).value = val;}setSelectValue("leaveCode",' 
cat /www/channel.conf 
echo ');</script>'

echo '<img id="image-map" usemap="#image-map"  src="../p2_ship_default.png" width="100%">'
echo "<map name='image-map'>"
echo   ' <area shape="rect" coords="204,5,807,430" href="/cgi-bin/up.cgi" title="Up" alt="Up" target="_self">'
echo   ' <area shape="rect" coords="258,458,743,933" href="/cgi-bin/stop.cgi" title="Stop" alt="Stop" target="_self">'
echo   ' <area shape="rect" coords="218,965,795,1360" href="/cgi-bin/down.cgi" title="Down" alt="Down" target="_self">'
echo "</map>"
echo '<script src="../jquery.min.js" type="text/javascript">'
echo '</script>' 
echo '<script src="../imageMapResizer.min.js" type="text/javascript">'
echo '</script>'
echo '<script  type="text/javascript">'
echo 'imageMapResize();'
echo '</script>'
echo ""
echo "</body>"
echo "</html>"



