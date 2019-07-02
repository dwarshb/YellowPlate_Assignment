# YellowPlate_Assignment
It is Android Application which is used to authenticate users using Github API 
"https://github.com/login/oauth/authorize/?client_id='YOUR-CLIENT-ID' " 

And helps them to get the list of github_users using API 
"https://api.github.com/users"

User can also search for particular github_user from list by writing their name or id on search-bar,
and visit their github_profile by selecting them.

RetroFit is Used to fetch json data from API.:
'com.squareup.retrofit2:retrofit:2.3.0'
'com.squareup.retrofit2:converter-gson:2.2.0'

Github Library to request Token from github	
'com.github.alorma:github-sdk:3.2.5'