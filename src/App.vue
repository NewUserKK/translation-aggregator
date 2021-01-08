<template>
  <div ref="app" id="app">
    <router-view />
  </div>
</template>

<style>
  * {
    box-sizing: border-box;
    font-family: 'Work Sans', sans-serif;
  }

  *:focus {
    outline: none;
  }

  html {
    width: 100%;
    height: 100%;
  }

  body {
    width: 100%;
    height: 100%;

    margin: 0 0 0 0 !important;
  }

  p {
    margin: 0;
    padding: 0;
  }

  h1 {
    margin: 0;
    padding: 0;

    font-weight: bold;
    font-size: 18px;
  }

  h2 {
    margin: 0;
    padding: 0;

    font-weight: bold;
    font-size: 16px;
  }

  ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  #app {
    font-family: Inter, Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;

    width: 100%;
    height: 100%;

    color: #2c3e50;
  }

  button {
    cursor: pointer;
  }

  .no-text-selection {
    -webkit-user-select: none; /* Safari */
    -ms-user-select: none; /* Internet Explorer/Edge */
    user-select: none;
  }

  /*::placeholder {*/
  /*  !* Chrome, Firefox, Opera, Safari 10.1+ *!*/
  /*  color: #3e4958 !important;*/
  /*  opacity: 1 !important; !* Firefox *!*/
  /*}*/

  /*:-ms-input-placeholder {*/
  /*  !* Internet Explorer 10-11 *!*/
  /*  color: #3e4958 !important;*/
  /*}*/

  /*::-ms-input-placeholder {*/
  /*  !* Microsoft Edge *!*/
  /*  color: #3e4958 !important;*/
  /*}*/
</style>
