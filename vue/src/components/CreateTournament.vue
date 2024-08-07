<template>
    <div id="createTournament">
        <form action="">
            <h1>Create New Tournament</h1>
            <div class="form-input-group">
                <label for="tournamentName">Tournament Name</label>
                <input type="text" id="tournamentName" v-model="tournament.tournamentName" required autofocus />
            </div>
            <div class="form-input-group">
                <label for="startDate">Start Date</label>
                <input type="date" id="startDate" v-model="tournament.startDate" required />
            </div>
            <div class="form-input-group">
                <label for="endDate">End Date</label>
                <input type="date" id="endDate" v-model="tournament.endDate" required />

            </div>
            <div class="form-input-group">
                <label for="description">Description</label>
                <textarea id="description" v-model="tournament.description" required rows="4" cols="50"></textarea>
            </div>
            <div class="form-input-group">
                <label for="isOnline">Online Tournament?</label>
                <input type="checkbox" id="isOnline" v-model="tournament.isOnline" />
            </div>
            <div class="form-input-group">
                <label for="isScrim">Scrimmage?</label>
                <input type="checkbox" id="isScrim" v-model="tournament.isScrim" />
            </div>
            <div class="form-input-group">
                <label for="location">Location</label>
                <input type="text" id="location" v-model="tournament.location" />
            </div>
            <button type="submit">Create</button>
        </form>
    </div>
</template>

<script>
export default {
    name: 'CreateTournament',
    data() {
        return {
            tournament: {
                tournamentName: '',
                startDate: '',
                endDate: '',
                description: '',
                isOnline: false,
                isScrim: false,
                location: '',

            }
        }
    }



}
</script>


<style>
#createTournament {
    display: flex;
    justify-content: center;
    align-items: center;
    
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
