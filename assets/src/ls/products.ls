$ document .ready !->

	filters =
		minPrice: null
		maxPrice: null
		categories: []
		pageSize: 20
		orderBy: 'price'
		sortOrder: 'asc'
		query: ''
		pageNumber: 0

	buildFilters = !->

		minPrice = parseInt($ \#filter-price-min .val! || -1)
		maxPrice = parseInt($ \#filter-price-max .val! || -1)

		filters.minPrice = minPrice if minPrice != -1
		filters.maxPrice = maxPrice if maxPrice != -1

		filters.pageSize = parseInt($ \#modifier-page-size .find \.modifier-option.selected .text!)

		$filterOption = $ \#modifier-order-input .find \option:selected

		filterOption = $filterOption .val! .split '-'

		filters.orderBy = filterOption[0]
		filters.sortOrder = filterOption[1]

		filters.query = $ \#navbar-query-input .val!

		filters.pageNumber = parseInt($ \#filter-page .data('page'))

		$selectedCheckboxes = $ '#category-filter input:checked'

		if $selectedCheckboxes.length
			filters.categories = $selectedCheckboxes
				.map ->
					$ this .val!
				.toArray!
		else

			$categoryLabels = $ '#category-filter label.parent-label'

			if $categoryLabels.length > 0

				filters.categories = $categoryLabels
					.map ->
						$ this .data 'id'
					.toArray!
			else
				filters.categories = $ '.applied-filter[data-category-id]'
					.map ->
						$ this .data 'category-id'
					.toArray!

		console.log filters

	buildFilters!

	

	$ \#modifier-order-input .change (e) !->

		e.preventDefault!

		buildFilters!

		console.log($.param(filters))

		window.location.search = $.param(filters)

	$ \#filter-price-set .click (e) !->
		e.preventDefault!

		buildFilters!

		window.location.search = $.param(filters)

	$ '#filter-price-min, #filter-price-max' .keydown (e) !->
		console.log e.keyCode
		if e.keyCode == 13
			$ \#filter-price-set .click!

	$ \.modifier-option .click (e) !->
		e.preventDefault!

		$self = $ this
		$row = $self .parent!

		if $self .hasClass \selected
			return

		$row .find \.selected .toggleClass \selected
		$self .toggleClass \selected

		buildFilters!

		window.location.search = $.param(filters)

	$ \#navbar-search .on \submit, (e) !->

		# e.preventDefault!

		if $ .trim($ \#navbar-query-input .val!) == ''
			e.preventDefault!

		# buildFilters!

		# window.location.search = $.param(filters)


	$ \.filter-page-action .click (e) !-> 
		e.preventDefault!

		$self = $ this

		if $self .parent! .hasClass \disabled
			return

		if $self .parent! .hasClass \previous
			filters.pageNumber = 0

		if $self .parent! .hasClass \pre
			filters.pageNumber -= 1;

		if $self .parent! .hasClass \ne
			filters.pageNumber += 1;

		if $self .parent! .hasClass \next
			filters.pageNumber = parseInt($ \#filter-page .data('last-page'))

		window.location.search = $.param(filters)

	$ \#category-filter-button .click (e) !->
		e.preventDefault!

		buildFilters!

		window.location.search = $.param(filters)

