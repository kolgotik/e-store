# e-store (MetaVerse)
This is my implementation of online store for selling small electronics.

> [!IMPORTANT]
> 95% of the app's front end is template, some parts of the front end I did myself.
> I can't give link to the source where I got the template due to absence of the very link.



    
# Key Features!
Since this app is e-store, it has basic login and registration based on the Spring Security.
- Login:
  - User can login with e-mail and password. Alternatively user can login via Google account.
- Registration:
  - In order for user to sign up successfully, user should confirm email as seen below.
  
  ![image](https://github.com/kolgotik/e-store/assets/90344391/84454ace-e192-44c5-a9b5-e482a4d76fea)

- Purchase simulation:
  - If user is not signed in and tries to buy smth, app will forward user to the login page.
  - If user wants to buy an item but has 0$ balance user will see appropriate message:
    
    ![image](https://github.com/kolgotik/e-store/assets/90344391/b1b739a8-1419-4dd4-8740-fbc074af029b)
  - In this app user can't buy stuff for free, and user should top up the balance! Luckily there is no need for real money! Logged user just can go to profile and top up the balance up to 5000$ per replenishment.

    Before:
    ![image](https://github.com/kolgotik/e-store/assets/90344391/8b24887f-ab11-4052-b8ca-eee352674e5e)

    After:
    ![image](https://github.com/kolgotik/e-store/assets/90344391/117f69e1-1b06-444c-8478-a7eb86489fa6)
  - With enough balance user can buy desired item!
    
- Item rating.
  - User can rate purchased item only when user actually purchased it. User can give item a score from 1 to 10 and leave a comment.
  - The average rating of a certain item will be calculated based on user ratings.
