package com.victor.Gerador.Geradores;

import com.victor.Gerador.Model.Categoria;
import com.victor.Gerador.Model.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorProduto {
    private static final Random random = new Random();

    private static final String[] nomes = {
            "PC Gamer",
            "PC Desktop",
            "Workstation",
            "Notebook Gamer",
            "Notebook Ultrafino",
            "Notebook Corporativo",
            "Mini PC",

            "Intel Core i3",
            "Intel Core i5",
            "Intel Core i7",
            "Intel Core i9",
            "AMD Ryzen 5",
            "AMD Ryzen 7",
            "AMD Ryzen 9",

            "GeForce RTX 3050",
            "GeForce RTX 4060",
            "GeForce RTX 4070",
            "GeForce RTX 4080",
            "Radeon RX 6600",
            "Radeon RX 7600",

            "Placa-Mãe ASUS",
            "Placa-Mãe Gigabyte",
            "Placa-Mãe MSI",
            "Placa-Mãe ASRock",

            "Memória RAM DDR4 8GB",
            "Memória RAM DDR4 16GB",
            "Memória RAM DDR5 16GB",
            "Memória RAM DDR5 32GB",
            "Memória RAM DDR5 64GB",

            "SSD SATA 480GB",
            "SSD SATA 1TB",
            "SSD NVMe 500GB",
            "SSD NVMe 1TB",
            "SSD NVMe 2TB",
            "HD 1TB",
            "HD 2TB",
            "HD Externo 2TB",
            "Pen Drive 64GB",
            "Pen Drive 128GB",

            "Monitor LED 21.5",
            "Monitor Full HD 24",
            "Monitor QHD 27",
            "Monitor 4K 32",
            "Monitor Gamer 144Hz",
            "Monitor Gamer 240Hz",

            "Teclado Mecânico RGB",
            "Teclado Sem Fio",
            "Teclado Gamer",
            "Mouse Óptico",
            "Mouse Gamer RGB",
            "Mouse Sem Fio",
            "Mousepad Gamer",

            "Headset Gamer",
            "Headset Bluetooth",
            "Fone de Ouvido USB",
            "Microfone Condensador",
            "Webcam Full HD",
            "Webcam 4K",
            "Caixa de Som USB",

            "Roteador Wi-Fi 6",
            "Roteador Wi-Fi 7",
            "Switch 8 Portas",
            "Switch 24 Portas",
            "Adaptador Wi-Fi USB",
            "Placa de Rede Gigabit",

            "Fonte 500W",
            "Fonte 650W",
            "Fonte 750W",
            "Fonte 850W",
            "Cooler para Processador",
            "Water Cooler 240mm",
            "Water Cooler 360mm",
            "Ventoinha RGB",

            "Gabinete Gamer",
            "Gabinete Mid Tower",
            "Gabinete Full Tower",
            "Nobreak 1200VA",
            "Filtro de Linha",
            "Cabo HDMI",
            "Cabo DisplayPort",
            "Cabo USB-C",
            "Adaptador USB",
            "Hub USB",
            "Dock Station",

            "Smartphone Android",
            "Smartphone Premium",
            "Tablet Android",
            "Tablet Profissional",

            "Impressora Laser",
            "Impressora Multifuncional",
            "Impressora Jato de Tinta",

            "Cartão de Memória 128GB",
            "Cartão de Memória 256GB",
            "Leitor de Cartão USB",
            "Gravador DVD Externo"
    };

    public static List<Produto> gerarProdutos(
            int quantidade,
            List<Categoria> categorias) {

        List<Produto> produtos = new ArrayList<>();

        if (categorias == null || categorias.isEmpty()) {
            throw new IllegalArgumentException(
                    "É necessário gerar as categorias primeiro."
            );
        }

        for (int i = 1; i <= quantidade; i++) {

            Produto produto = new Produto();

            String nome = nomes[random.nextInt(nomes.length)];

            Categoria categoria = categorias.get(
                    random.nextInt(categorias.size())
            );

            BigDecimal preco = BigDecimal.valueOf(
                    random.nextInt(95000) + 500, 2
            );

            BigDecimal percentualCusto = BigDecimal.valueOf(
                    50 + random.nextInt(36)
            );

            BigDecimal custo = preco
                    .multiply(percentualCusto)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            produto.setCusto(custo);

            produto.setId((long) i);
            produto.setNome(nome + " Modelo " + i);
            produto.setPreco(preco);
            produto.setEstoque(random.nextInt(201));
            produto.setCategoria(categoria);


            produtos.add(produto);
        }

        return produtos;
    }
}
