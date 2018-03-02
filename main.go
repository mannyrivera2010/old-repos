package main

import (
    "encoding/json"
    "fmt"
    "log"
    "net/http"

    "github.com/gorilla/mux"
    "github.com/mannyrivera2010/golang-learning-1/models"
)

var movies = map[string]*models.Movie{
    "tt0076759": &models.Movie{Title: "Star Wars: A New Hope", Rating: "8.7", Year: "1977"},
    "tt0082971": &models.Movie{Title: "Indiana Jones: Raiders of the Lost Ark", Rating: "8.6", Year: "1981"},
}

func main() {
    router := mux.NewRouter()
    router.HandleFunc("/movies", listMovies).Methods("GET")
    router.HandleFunc("/movie/{imdbKey}", getMovie).Methods("GET")
    router.HandleFunc("/movie/{imdbKey}", createMovie).Methods("POST")
    router.HandleFunc("/movie/{imdbKey}", deleteMovie).Methods("DELETE")

    http.ListenAndServe(":8080", router)
}

func listMovies(res http.ResponseWriter, req *http.Request) {
    res.Header().Set("Content-Type", "application/json")
    outgoingJSON, error := json.Marshal(movies)
    if error != nil {
        log.Println(error.Error())
        http.Error(res, error.Error(), http.StatusInternalServerError)
        return
    }
    fmt.Fprint(res, string(outgoingJSON))
}


func getMovie(res http.ResponseWriter, req *http.Request) {
  res.Header().Set("Content-Type", "application/json")
  vars := mux.Vars(req)
  imdbKey := vars["imdbKey"]

  movie, ok := movies[imdbKey]
  if !ok {
      res.WriteHeader(http.StatusNotFound)
      fmt.Fprint(res, string("Movie not found"))
  }
  outgoingJSON, error := json.Marshal(movie)
  if error != nil {
      log.Println(error.Error())
      http.Error(res, error.Error(), http.StatusInternalServerError)
      return
  }
  fmt.Fprint(res, string(outgoingJSON))

}


func createMovie(res http.ResponseWriter, req *http.Request) {
  res.Header().Set("Content-Type", "application/json")
  vars := mux.Vars(req)
  imdbKey := vars["imdbKey"]

  movie := new(models.Movie)
  decoder := json.NewDecoder(req.Body)
  error := decoder.Decode(&movie)
  if error != nil {
      log.Println(error.Error())
      http.Error(res, error.Error(), http.StatusInternalServerError)
      return
  }
  movies[imdbKey] = movie
  outgoingJSON, err := json.Marshal(movie)
  if err != nil {
      log.Println(error.Error())
      http.Error(res, err.Error(), http.StatusInternalServerError)
      return
  }
  res.WriteHeader(http.StatusCreated)
  fmt.Fprint(res, string(outgoingJSON))
}


func deleteMovie(res http.ResponseWriter, req *http.Request) {
  res.Header().Set("Content-Type", "application/json")
  vars := mux.Vars(req)
  imdbKey := vars["imdbKey"]

  delete(movies, imdbKey)
  res.WriteHeader(http.StatusNoContent)
}
