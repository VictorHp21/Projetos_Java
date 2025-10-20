package Model.entities;

import Model.utils.HashUtil;

public class Usuario {
    private String email;
    private String senhaHashHex;

    public Usuario(String email, String senhaHashHex) {
        this.email = email;
        this.senhaHashHex = senhaHashHex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHashHex() {
        return senhaHashHex;
    }

    public void setSenhaHashHex(String senhaHashHex) {
        this.senhaHashHex = senhaHashHex;
    }

    public static Usuario cadastrar(String email, String senhaPlain) {
        byte[] hash = HashUtil.sha256b(senhaPlain);
        String hex = HashUtil.bytesToHex(hash);
        return new Usuario(email, hex);
    }


    public boolean autenticar(String senhaDigitada) {
        byte[] hashDigitado = HashUtil.sha256b(senhaDigitada);
        byte[] hashArmazenado = HashUtil.hexToBytes(this.senhaHashHex);
        return HashUtil.secureEquals(hashDigitado, hashArmazenado);
    }

}
