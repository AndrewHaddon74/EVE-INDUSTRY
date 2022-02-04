# EVE-INDUSTRY

***WARNING***
Incomplete Project

This is a tool for the video game EvE Online, the purpose of this tool is to gauge profitability of in game industrial activities.  Currently this tool has a GUI interface that lets you search for the item you are wanting to build as well as select the number of items to make, also selecting the structure cost reductions provided by both a structure and rigs. Once the initial selection is loaded up it fills a table complete with selection boxes for selecting if you want to build certain subcomponents or buy them. As of right now this project only calculates material total, material costs and output value, it currently does not take taxes into account. 

This is a Java-based project that utilizes XML and SQL data to find real time price data from the games API end points, using the local SQL file to get exact requirements. GUI was built using IntelliJ’s Java GUI builder and parts of the main table where hand made to support check box selections. The GUI class is where most everything happens including a hand dandy to clipboard button to export all of your material needs at once. The math is not very complex but there is a lot of small pieces of it going on for rounding up and down to the nearest 5 or 200. 

To Do Items
implement the much more complex math for the in-game taxes. 
Break up the god class that is the primary UI, have it call functions instead of doing everything right there
Coloration in the background so everything is not a mat white.
Improve the table so it dynamically creates spacer lines between component construction lists

To run this program you need to get the latest eve SDE from the following link https://www.fuzzwork.co.uk/dump/, this then needs to be moved into the same folder as the Java program before everything is compiled.
