$ document .ready !->

	$ \#navbar-search .on \submit, (e) !->

		if $ .trim($ \#navbar-query-input .val!) == ''
			e.preventDefault!