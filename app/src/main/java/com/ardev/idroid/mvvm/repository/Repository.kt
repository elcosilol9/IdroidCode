fun register(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

            emit(Resource.Success(data = it))
          
            emit(Resource.Error(message = "Error"?: ""))
        


    }