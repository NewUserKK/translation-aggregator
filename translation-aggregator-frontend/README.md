# vue-template-project
New-project template for Vue.js

## Usage
```
git clone https://github.com/NewUserKK/vue-template-project
mv vue-template-project my-project
cd my-project
npm install
npm run serve
```
Don't forget to change project name in `package.json`!


## Packing and building
`pack.sh` script is designed to help pack static resources and move them to static server folder.

Usage:
`pack.sh [dev/prod] [path-to-server]`
* 1st argument: build type. Default: dev
* 2nd argument: path to root server folder. Default: none, modify script with your path


## Used libraries and dependencies
* Typescript
* vue-router -- page routing
* vuex -- flux architecture
* axios -- network requests
* vuex-persistedstate -- persisting vuex state after reloading a page


## Utils
* data/common
  * Either.ts (and Result.ts): `Either` type implementation.

    File also has structure `EitherMatcher` to simplify work with `Either`. Common usage:
    ```
    (await Service.makeRequest()).matcher()
      .onRight(result => { /* processResult */ }
      .onLeft(error => { /* process error */ }
      .match()
    ```
    Callbacks are called immediatelly so `match()` function call is not necessary (for example, if result type is `void`)
    
    `Result` is just typealias for Either<Error, T>
* service-utils: util-functions to reduce boilerplate in `axios` calls. Basically just checks if response was successful
  and returns `Either.Right` if it was or `Either.Left` if it wasn't.
  
  Common usage:
  ```
  async function getUsers(): Promise<Result<List<User>>> {
    return handleSuccess(axiosInstance.get("/users"))
  }
  ```
  
## Authorization
Project has some authorization help logic by default.

Main idea is:
* user goes to `/`
* `/` redirects to `/login` with basic login form (`view/views/auth/LoginPage.vue` component)
* "Sign in" button press redirects to `/authenticated` (`view/views/auth/AuthenticatedPage.vue`) that 
  does some initial actions (usually Store dispatches and redirects to `/main` (`view/views/MainPage.vue`)
* default axios instance has interceptor on `401` and `403` codes that redirects user to `/login` when they are received
* every time user goes to `/login`, GET request is made to `${BASE_URL}/ping` and if it is successful, then it gets redirected
  to `/authenticated` (in case user got to this page by himself, from browser)


## Linters
There are certain modifications to Prettier and ESLint rules that can be observed in `.eslintrc.js`


## IDEa notes
### Prettier
This project is designed to work with Prettier so Prettier plugin should be installed. After that, format file with `Ctrl+Alt+Shift+P` instead of default `Ctrl+Alt+L`

### Template
You can add following file template to create new Vue components from context menu easily:

```
<template>

</template>

<script lang="ts">
import {Component, Vue} from "vue-property-decorator";

@Component
export default class ${NAME} extends Vue {

}
</script>

<style scoped>

</style>
```
