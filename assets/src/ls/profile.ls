$ document .ready !->

	$ '#myTab a, #itemsTab a, #salesTab a' .click (e) !->
		e.preventDefault!

		$ this .tab \show

	$ 'ul.nav-tabs > li > a' .on 'shown.bs.tab', (e) !->
		id = $ e.target .attr \href .substr(1)
		window.location.hash = id

	$ 'form' .submit (e) !->

		action = $ this .attr \action

		$ this .attr \action, action + window.location.hash

	onHashChange = (hash) !->

		parts = hash.split \-
		mainHash = parts[0]

		$ "\#myTab a[href='#mainHash']" .tab \show

		if parts.length > 1
			$ "\#itemsTab a[href='#hash']" .tab \show
			$ "\#salesTab a[href='#hash']" .tab \show

		

	onHashChange window.location.hash

	$ window .on \hashchange, !->
		onHashChange window.location.hash