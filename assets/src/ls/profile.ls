$ document .ready !->

	$ '#myTab a' .click (e) !->
		e.preventDefault!

		$ this .tab \show

	$ 'ul.nav-tabs > li > a' .on 'shown.bs.tab', (e) !->
		id = $ e.target .attr \href .substr(1)
		window.location.hash = id

	$ 'form' .submit (e) !->

		action = $ this .attr \action

		$ this .attr \action, action + window.location.hash

	hash = window.location.hash;
	$ "\#myTab a[href='#hash']" .tab \show