<template>
  <div id="capstone-app">
   
    <router-view />
  </div>
</template>

<style>
#nav{
  text-align: right;
  font-family: Verdana;
 padding: 1rem; 
}
#nav a {
  background-color: #6e8fe2;
  text-decoration: none; 
  color: black;
  border: 1px solid light grey; 
  border-radius: 4px; 
  padding: 0.5rem 1rem; 
  transition: background-color 0.3s ease, color 0.3s ease; 
}
#nav a:hover {
  background-color: white; 
  color: blue; 
}

form {
    /* justify-content: center;
    align-items: center; */
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 2rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    width: 100%;
    max-width: 400px;
}

h1 {
    margin-bottom: 1rem;
}

.form-input-group {
    margin-bottom: 1rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
}

input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
    width: 100%;
    padding: 0.75rem;
    border: 2px solid #007bff;
    border-radius: 4px;
    background-color: #007bff;
    color: white;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease, border-color 0.3s ease;
}

button:hover {
    background-color: #0056b3;
    border-color: #004080;
}

button:active {
    background-color: #004080;
    border-color: #00264d;
}
</style>
