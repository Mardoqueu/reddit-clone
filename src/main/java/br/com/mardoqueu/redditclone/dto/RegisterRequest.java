package br.com.mardoqueu.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*The API call should contain the request body which is of type RegisterRequest.
Through this class we are transferring the user details like username, password and email as part of the RequestBody.
We call this kind of classes as a DTO (Data Transfer Object).*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}