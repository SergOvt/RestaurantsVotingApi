package ru.voting.api.restaurants.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.model.Vote;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final CrudUserRepository crudUserRepository;
    private final CrudVoteRepository crudVoteRepository;

    @Autowired
    public UserRepositoryImpl(CrudUserRepository crudUserRepository, CrudVoteRepository crudVoteRepository) {
        this.crudUserRepository = crudUserRepository;
        this.crudVoteRepository = crudVoteRepository;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll();
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    @Override
    public void setVote(Vote vote) {
        crudVoteRepository.save(vote);
    }

}
