package uk.co.techswitch.services;

import uk.co.techswitch.library.models.FilmWithCredits;
import uk.co.techswitch.metadata.MetadataApiClient;
import uk.co.techswitch.metadata.models.FilmMetadata;
import uk.co.techswitch.models.FilmDetailsModel;
import uk.co.techswitch.models.FilmModel;
import uk.co.techswitch.models.SearchModel;
import uk.co.techswitch.library.LibraryApiClient;
import uk.co.techswitch.ratings.RatingsApiClient;
import uk.co.techswitch.ratings.models.FilmRating;

import java.util.List;
import java.util.stream.Collectors;

public class FilmsService {
    private final LibraryApiClient libraryApiClient;
    private final MetadataApiClient metadataApiClient;
    private final RatingsApiClient ratingsApiClient;

    public FilmsService(LibraryApiClient libraryApiClient,
                        MetadataApiClient metadataApiClient,
                        RatingsApiClient ratingsApiClient) {
        this.libraryApiClient = libraryApiClient;
        this.metadataApiClient = metadataApiClient;
        this.ratingsApiClient = ratingsApiClient;
    }

    public List<FilmModel> getFilms(SearchModel searchModel) {
        return libraryApiClient
                .getAllFilms(searchModel)
                .stream()
                .map(FilmModel::new)
                .collect(Collectors.toList());
    }

    public FilmDetailsModel getFilm(String id) {
        FilmWithCredits film = libraryApiClient.getFilm(id);
        FilmMetadata metadata = metadataApiClient.getFilmMetadata(film.getTmdbId());
        FilmRating rating = ratingsApiClient.getFilmRating(film.getTmdbId());
        return new FilmDetailsModel(film, metadata, rating);
    }
}
