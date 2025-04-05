package com.chatAny.chatapi.repository.user;

//@Repository
//public class MongoUserRepository implements UserRepository {
//
//    private DefaultMongoUserRepository defaultMongoUserRepository;
//
//    public MongoUserRepository(DefaultMongoUserRepository defaultMongoUserRepository) {
//        this.defaultMongoUserRepository = defaultMongoUserRepository;
//    }
//
//    @Override
//    public User findByName(String userName) {
//        return defaultMongoUserRepository.findByName(userName);
//    }
//
//    @Override
//    public User find(String id) {
//        return defaultMongoUserRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public User save(String userName) {
//        return defaultMongoUserRepository.save(new User(null, userName));
//    }
//}