/**
 * 
 */
var $go = go.GraphObject.make;

var myDiagram =
	$go(go.Diagram, "apiDiagramDiv",
			{
		"undoManager.isEnabled": true, // enable Ctrl-Z to undo and Ctrl-Y to redo
		layout: $go(go.TreeLayout, // specify a Diagram.layout that arranges trees
				{ angle: 90, layerSpacing: 35 })
			});

//the template we defined earlier
myDiagram.nodeTemplate =
	$go(go.Node, "Horizontal",
			{ background: "#44CCFF" },
			$go(go.TextBlock, "Default Text",
					{ margin: 50, stroke: "white", font: "bold 16px sans-serif" },
					new go.Binding("text", "name"))
	);

//define a Link template that routes orthogonally, with no arrowhead
myDiagram.linkTemplate =
	$go(go.Link,
			{ routing: go.Link.Orthogonal, corner: 5 },
			$go(go.Shape, { strokeWidth: 3, stroke: "#555" })); // the link shape

var model = $go(go.TreeModel);
model.nodeDataArray =
	[
		{ key: "1",              name: "BOOK SERVICE" }
		/*{ key: "2", parent: "1", name: "Demeter" },
		{ key: "3", parent: "1", name: "Copricat" },
		{ key: "4", parent: "3", name: "Jellylorum" },
		{ key: "5", parent: "3", name: "Alonzo" },
		{ key: "6", parent: "2", name: "Munkustrap" }*/
		];
myDiagram.model = model;
