package com.victor.Gerador.Geradores;

import com.victor.Gerador.Config.Parametros;
import com.victor.Gerador.Model.Categoria;
import com.victor.Gerador.Model.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorProduto {

    private static final Random random = Parametros.RANDOM;

    /**
     * Catálogo: nome do produto, categoria a que pertence e faixa de preço
     * plausível (em reais). Cada produto fica sempre na categoria certa.
     */
    private static final Object[][] CATALOGO = {
            // nome                         categoria                 preço mín  preço máx
            {"PC Gamer",                    "Computadores",           3500, 9000},
            {"PC Desktop",                  "Computadores",           1800, 4000},
            {"Workstation",                 "Computadores",           7000, 15000},
            {"Mini PC",                     "Computadores",           1200, 3000},

            {"Notebook Gamer",              "Notebooks",              4500, 10000},
            {"Notebook Ultrafino",          "Notebooks",              3500, 7000},
            {"Notebook Corporativo",        "Notebooks",              3000, 6000},

            {"Intel Core i3",               "Processadores",          500, 800},
            {"Intel Core i5",               "Processadores",          900, 1500},
            {"Intel Core i7",               "Processadores",          1800, 2600},
            {"Intel Core i9",               "Processadores",          3000, 4200},
            {"AMD Ryzen 5",                 "Processadores",          700, 1200},
            {"AMD Ryzen 7",                 "Processadores",          1500, 2300},
            {"AMD Ryzen 9",                 "Processadores",          2800, 4000},

            {"GeForce RTX 3050",            "Placas de Vídeo",        1200, 1600},
            {"GeForce RTX 4060",            "Placas de Vídeo",        1800, 2300},
            {"GeForce RTX 4070",            "Placas de Vídeo",        3500, 4500},
            {"GeForce RTX 4080",            "Placas de Vídeo",        6500, 8500},
            {"Radeon RX 6600",              "Placas de Vídeo",        1300, 1700},
            {"Radeon RX 7600",              "Placas de Vídeo",        1700, 2200},

            {"Placa-Mãe ASUS",              "Placas-Mãe",             700, 1800},
            {"Placa-Mãe Gigabyte",          "Placas-Mãe",             650, 1600},
            {"Placa-Mãe MSI",               "Placas-Mãe",             650, 1700},
            {"Placa-Mãe ASRock",            "Placas-Mãe",             550, 1300},

            {"Memória RAM DDR4 8GB",        "Memórias RAM",           120, 200},
            {"Memória RAM DDR4 16GB",       "Memórias RAM",           220, 350},
            {"Memória RAM DDR5 16GB",       "Memórias RAM",           350, 500},
            {"Memória RAM DDR5 32GB",       "Memórias RAM",           650, 900},
            {"Memória RAM DDR5 64GB",       "Memórias RAM",           1300, 1800},

            {"SSD SATA 480GB",              "Armazenamento",          180, 260},
            {"SSD SATA 1TB",                "Armazenamento",          320, 450},
            {"SSD NVMe 500GB",              "Armazenamento",          250, 350},
            {"SSD NVMe 1TB",                "Armazenamento",          400, 600},
            {"SSD NVMe 2TB",                "Armazenamento",          800, 1100},
            {"HD 1TB",                      "Armazenamento",          250, 350},
            {"HD 2TB",                      "Armazenamento",          380, 500},

            {"Monitor LED 21.5",            "Monitores",              450, 650},
            {"Monitor Full HD 24",          "Monitores",              650, 900},
            {"Monitor QHD 27",              "Monitores",              1300, 1900},
            {"Monitor 4K 32",               "Monitores",              2500, 3800},
            {"Monitor Gamer 144Hz",         "Monitores",              1000, 1500},
            {"Monitor Gamer 240Hz",         "Monitores",              1800, 2800},

            {"Teclado Mecânico RGB",        "Teclados",               250, 500},
            {"Teclado Sem Fio",             "Teclados",               120, 250},
            {"Teclado Gamer",               "Teclados",               200, 400},

            {"Mouse Óptico",                "Mouses",                 30, 70},
            {"Mouse Gamer RGB",             "Mouses",                 120, 300},
            {"Mouse Sem Fio",               "Mouses",                 70, 180},

            {"Headset Gamer",               "Headsets e Fones",       200, 600},
            {"Headset Bluetooth",           "Headsets e Fones",       150, 450},
            {"Fone de Ouvido USB",          "Headsets e Fones",       60, 150},

            {"Impressora Laser",            "Impressoras",            800, 1600},
            {"Impressora Multifuncional",   "Impressoras",            700, 1500},
            {"Impressora Jato de Tinta",    "Impressoras",            400, 900},

            {"Smartphone Android",          "Smartphones",            1200, 2500},
            {"Smartphone Premium",          "Smartphones",            4500, 9000},

            {"Tablet Android",              "Tablets",                900, 1800},
            {"Tablet Profissional",         "Tablets",                3500, 8000},

            {"Smart TV 43 Full HD",         "Smart TVs",              1500, 2200},
            {"Smart TV 55 4K",              "Smart TVs",              2500, 3800},
            {"Smart TV 65 4K",              "Smart TVs",              3800, 6000},

            {"Microfone Condensador",       "Periféricos",            250, 700},
            {"Webcam Full HD",              "Periféricos",            180, 400},
            {"Webcam 4K",                   "Periféricos",            600, 1200},
            {"Caixa de Som USB",            "Periféricos",            80, 250},

            {"Roteador Wi-Fi 6",            "Redes",                  400, 900},
            {"Roteador Wi-Fi 7",            "Redes",                  1200, 2500},
            {"Switch 8 Portas",             "Redes",                  150, 350},
            {"Switch 24 Portas",            "Redes",                  800, 1600},
            {"Adaptador Wi-Fi USB",         "Redes",                  60, 150},
            {"Placa de Rede Gigabit",       "Redes",                  80, 200},

            {"Fonte 500W",                  "Fontes de Alimentação",  250, 380},
            {"Fonte 650W",                  "Fontes de Alimentação",  380, 550},
            {"Fonte 750W",                  "Fontes de Alimentação",  500, 750},
            {"Fonte 850W",                  "Fontes de Alimentação",  700, 1000},

            {"Gabinete Gamer",              "Gabinetes",              350, 800},
            {"Gabinete Mid Tower",          "Gabinetes",              250, 500},
            {"Gabinete Full Tower",         "Gabinetes",              600, 1200},

            {"Cooler para Processador",     "Refrigeração",           80, 250},
            {"Water Cooler 240mm",          "Refrigeração",           400, 700},
            {"Water Cooler 360mm",          "Refrigeração",           650, 1100},
            {"Ventoinha RGB",               "Refrigeração",           40, 100},

            {"Nobreak 1200VA",              "Nobreaks e Energia",     700, 1100},
            {"Filtro de Linha",             "Nobreaks e Energia",     40, 100},

            {"Cabo HDMI",                   "Cabos e Conectores",     25, 80},
            {"Cabo DisplayPort",            "Cabos e Conectores",     40, 100},
            {"Cabo USB-C",                  "Cabos e Conectores",     30, 90},
            {"Hub USB",                     "Cabos e Conectores",     60, 180},
            {"Dock Station",                "Cabos e Conectores",     400, 1200},

            {"Mousepad Gamer",              "Acessórios Gamer",       50, 150},
            {"Controle Gamer",              "Acessórios Gamer",       250, 450},
            {"Cadeira Gamer",               "Acessórios Gamer",       900, 2200},

            {"HD Externo 2TB",              "Armazenamento Externo",  450, 650},
            {"Pen Drive 64GB",              "Armazenamento Externo",  30, 60},
            {"Pen Drive 128GB",             "Armazenamento Externo",  50, 90},
            {"Cartão de Memória 128GB",     "Armazenamento Externo",  60, 110},
            {"Cartão de Memória 256GB",     "Armazenamento Externo",  110, 200},

            {"Windows 11 Pro",              "Softwares",              800, 1200},
            {"Pacote Office",               "Softwares",              400, 700},
            {"Antivírus (licença 1 ano)",   "Softwares",              80, 150},
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

            // Percorre o catálogo em ordem; se pedirem mais produtos que o
            // catálogo tem, recomeça com uma segunda linha ("Linha 2", ...).
            Object[] item = CATALOGO[(i - 1) % CATALOGO.length];
            int linha = (i - 1) / CATALOGO.length + 1;

            String nome = (String) item[0];
            Categoria categoria = buscarCategoria(categorias, (String) item[1]);
            int precoMin = (int) item[2];
            int precoMax = (int) item[3];

            Produto produto = new Produto();

            produto.setId((long) i);
            produto.setNome(linha == 1 ? nome : nome + " Linha " + linha);
            produto.setCategoria(categoria);
            produto.setPreco(precoComercial(precoMin, precoMax));

            // Custo entre 50% e 85% do preço de venda
            BigDecimal percentualCusto = BigDecimal.valueOf(50 + random.nextInt(36));
            produto.setCusto(
                    produto.getPreco()
                            .multiply(percentualCusto)
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            );

            produto.setEstoque(random.nextInt(201));

            produtos.add(produto);
        }

        return produtos;
    }

    /** Sorteia um preço na faixa e arredonda para um valor "de vitrine" (ex.: 1.499,90). */
    private static BigDecimal precoComercial(int min, int max) {

        int valor = min + random.nextInt(max - min + 1);
        int arredondado = Math.max(10, Math.round(valor / 10f) * 10);

        return BigDecimal.valueOf(arredondado).subtract(new BigDecimal("0.10"));
    }

    private static Categoria buscarCategoria(List<Categoria> categorias, String nome) {

        for (Categoria categoria : categorias) {
            if (categoria.getNome().equals(nome)) {
                return categoria;
            }
        }

        throw new IllegalStateException("Categoria não encontrada no catálogo: " + nome);
    }
}
