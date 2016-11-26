$ document .ready !->

	query = {}

	buildQuery = !->

		query.pageNumber = parseInt( $ \#filter-page .data \page )
		query.pageSize = parseInt($ \#modifier-page-size .find \.modifier-option.selected .text!)
		query.query = $ \#query-input .val!

		$filterOption = $ \#modifier-order-input .find \option:selected

		filterOption = $filterOption .val! .split '-'

		query.orderBy = filterOption[0].toUpperCase!
		query.sortOrder = filterOption[1].toUpperCase!

	submit = !->

		console.log query

		window.location.search = $ .param query, true

	resetPageNumber = !->

		query.pageNumber = 0

	buildQuery!

	$ \.filter-page-action .click (e) !->
		e.preventDefault!

		$self = $ this

		if $self .parent! .hasClass \disabled
			return

		if $self .parent! .hasClass \previous
			query.pageNumber = 0

		if $self .parent! .hasClass \pre
			query.pageNumber -= 1;

		if $self .parent! .hasClass \ne
			query.pageNumber += 1;

		if $self .parent! .hasClass \next
			query.pageNumber = parseInt($ \#filter-page .data('last-page'))

		submit!

	$ \#modifier-order-input .change (e) !->
		e.preventDefault!

		buildQuery!
		resetPageNumber!
		submit!

	$ \.modifier-option .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .parent!

		if $self .hasClass \selected
			return

		$row .find \.selected .toggleClass \selected
		$self .toggleClass \selected

		buildQuery!
		resetPageNumber!
		submit!