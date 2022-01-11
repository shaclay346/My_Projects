//based on the ID from the weaather api return the bootstrap icon
//associated with it
function displayIcon(id) {
    //big ass if else chain because I didn't want to look up how to do a switch statement
    if (id >= 200 && id <= 232) {
        return 'bi-cloud-lightning';
    }
    else if (id >= 300 && id <= 321) {
        //cloud rain
        return 'bi-cloud-rain';
    }
    else if (id >= 500 && id <= 531) {
        //cloud-rain-heavy
        return 'bi-cloud-rain-heavy';
    }
    else if (id >= 600 && id <= 622) {
        //cloud-snow
        return 'bi-cloud-snow';
    }
    else if (id >= 700 && id <= 771) {
        //cloud-fog
        return 'bi-cloud-fog';
    }
    else if (id == 781) {
        return 'bi-tornado';
    }
    else if (id == 800) {
        //cloud-fog
        return 'bi-sun';
    }
    else if (id >= 801 && id <= 804) {
        //cloudy
        return 'bi-cloudy';
    }
    else {
        return 'bi-patch-question';
    }
}

//method to update the modal once its clicked on
function updateModal(data, name) {
    daily = data.daily;

    //set the name of the city
    const modalHeader = document.getElementById('modal-title');
    modalHeader.innerText = `Forecast for ${name}`;

    const listItems = document.getElementsByClassName('list-group-item');

    //loop through the length of the daily weather data
    for (let i = 0; i < daily.length; i++) {
        max = Math.round(daily[i].temp.max);
        min = Math.round(daily[i].temp.min);
        desc = daily[i].weather[0].main;
        windSpeed = Math.round(daily[i].wind_speed);
        //make a template string with all the data we are going to display
        template = `${max}°F/${min}°F-${desc}, ${windSpeed} mph wind`;
        c = displayIcon(daily[i].weather[0].id);

        listItems[i].innerHTML = `<i class="${c} me-3"></i>${template}`;
    }
}

//I'm using this dicitionary to map coordinates to the Cities, needed for API call
let coordinates = {
    London: [51.5, 0.13],
    Denver: [39.7, 104.9],
    Lakeland: [27.86, 81.69],
    "Mexico City": [19.4, 99.13]
};

//get request 
function get(url) {
    return new Promise(function (resolve, reject) {
        let http = new XMLHttpRequest();

        http.open("GET", url, true);

        http.onload = function () {
            //what to do if it works goes here 
            if (http.readyState == 4 && http.status == 200) {
                resolve(JSON.parse(http.response));

            }
            else { // if it fails log response 
                reject(http.statusText);
            }
        }

        http.onerror = function () {
            reject(http.statusText);
        };

        http.send();
    });
}

//change the city name, temp and icon for the card
function changeCardData(card, data) {
    //change city name
    const city = card.querySelector('h5');
    city.innerText = data.name;

    //convert kelvin to Fahrenheit
    let temp = data.main.temp;
    temp -= 273.15;
    temp *= (9 / 5.0);
    temp += 32;

    //nmake template string to add the current weather
    let displayWeather = `${data.weather[0].main} - ${Math.round(temp)}°F`;
    //console.log(displayWeather);
    const weather = card.querySelector('h6');
    weather.innerText = displayWeather;

    const icon = card.querySelector('i');
    //set the class to be bootstrap item returned from function
    icon.classList = displayIcon(data.weather[0].id);
}




let loc = '33801';
//requests to the API 
let request = (`https://api.openweathermap.org/data/2.5/weather?q=London&appid=b139d88edbb994bbe4c2026a8de2ed12`);
let request1 = (`https://api.openweathermap.org/data/2.5/weather?q=${loc}&appid=b139d88edbb994bbe4c2026a8de2ed12`);
let request2 = (`https://api.openweathermap.org/data/2.5/weather?q=Denver&appid=b139d88edbb994bbe4c2026a8de2ed12`);
let request3 = (`https://api.openweathermap.org/data/2.5/weather?q=90005&appid=b139d88edbb994bbe4c2026a8de2ed12`);


//create promises 
let promise = get(request);
let promise1 = get(request1);
let promise2 = get(request2);
let promise3 = get(request3);

let a = [promise, promise1, promise2, promise3];

for (let i = 0; i < a.length; i++) {
    a[i].then(function (data) {
        console.log("you promised");

        // console.log(data);

        //get all cards
        const cards = document.getElementsByClassName('card col-2');

        //call method to change the cards data
        const city = cards[i];
        changeCardData(city, data);


    }).catch(function (error) {
        console.log('error:', error);
    });
}


//once the square is clicked on get the name of the city 
//then when they click on the forecast button do that http request and load
//the information 
const fuck = document.querySelectorAll('.card').forEach(elem => {
    elem.addEventListener('click', function () {
        const cityName = elem.querySelector('h5').innerText;

        //get the coordinates for the 4 hard coded cities
        let longLat = coordinates[cityName];
        //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&units=imperial&exclude=hourly,minutely,current&appid=b139d88edbb994bbe4c2026a8de2ed12
        let request = (`https://api.openweathermap.org/data/2.5/onecall?lat=${longLat[0]}&lon=${longLat[1]}&units=imperial&exclude=hourly,minutely,current&appid=b139d88edbb994bbe4c2026a8de2ed12`);


        let promise = get(request);


        promise.then(function (data) {
            //get the array for days and loop through the temps
            updateModal(data, cityName);

        }).catch(function (error) {
            console.log('error:', error);
        });
    });
});

//this happens before the promise 
console.log("code after promise");


const searchButton = document.getElementById('search')
searchButton.addEventListener('click', () => {
    console.log("button pressed")
    let enteredCity = document.getElementById("enteredCity").value;
    console.log("entered city is", enteredCity);


    let request = (`https://api.openweathermap.org/data/2.5/weather?q=${enteredCity}&appid=b139d88edbb994bbe4c2026a8de2ed12`);

    let promise = get(request)

    promise.then(function (data) {
        //make next call with long lat
        console.log(data);

        let lat = data.coord.lat;
        let lon = data.coord.lon;

        console.log(lat, lon);
       
        let request2 = (`https://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${lon}&units=imperial&exclude=hourly,minutely,current&appid=b139d88edbb994bbe4c2026a8de2ed12`);
        let promise2 = get(request2);

        promise2.then(function (data) {
            console.log(data)
            updateModal(data, enteredCity);

        }).catch(function (error) {
            console.log('7 day modal error:', error);
        });

    }).catch(function (error) {
        console.log("1 day call error: ", error)
        window.alert("Error. Please Try Again.")
        let errorModal= document.getElementById("error-modal");
        errorModal.style.visibility = "hidden";

    });

});

