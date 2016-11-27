Math.easeInOutCirc = (t, b, c, d) ->
	t /= d/2
	if t < 1
		return -c/2 * (Math.sqrt(1 - t*t) - 1) + b
	t -= 2
	c/2 * (Math.sqrt(1 - t*t) + 1) + b

Math.easeInOutExpo = (t, b, c, d) ->
	t /= d/2
	if t < 1
		return c/2 * Math.pow( 2, 10 * (t - 1) ) + b
	t--
	c/2 * ( -Math.pow( 2, -10 * t) + 2 ) + b

$ document .ready !->

	$approvedSales = $ \#approved-sales
	$rejectedSales = $ \#rejected-sales

	t = 0
	DURATION = 2000

	interval = setInterval !->
		b = 0
		c1 = $approvedSales.data(\end) + 1
		c2 = $rejectedSales.data(\end) + 1
		$approvedSales.text parseInt( Math.easeInOutExpo( t, b, c1, DURATION ) )
		$rejectedSales.text parseInt( Math.easeInOutExpo( t, b, c2, DURATION ) )
		if t >= DURATION
			clearInterval interval
		t += 10
	, 10

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