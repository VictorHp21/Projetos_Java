package com.victor.Gerador.Geradores;

import com.victor.Gerador.Model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class GeradorCategoria {
    public static List<Categoria> gerarCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        String[][] dados = {
                {"Computadores", "Desktops e computadores completos"},
                {"Notebooks", "Notebooks para uso pessoal e profissional"},
                {"Processadores", "Processadores Intel e AMD"},
                {"Placas de Vídeo", "Placas de vídeo para jogos e trabalho"},
                {"Placas-Mãe", "Placas-mãe para computadores"},
                {"Memórias RAM", "Memórias DDR4 e DDR5"},
                {"Armazenamento", "HDs, SSDs e unidades de armazenamento"},
                {"Monitores", "Monitores convencionais e gamers"},
                {"Teclados", "Teclados mecânicos e convencionais"},
                {"Mouses", "Mouses ópticos, sem fio e gamers"},
                {"Headsets e Fones", "Fones de ouvido e headsets"},
                {"Impressoras", "Impressoras e multifuncionais"},
                {"Smartphones", "Celulares e smartphones"},
                {"Tablets", "Tablets para uso pessoal e profissional"},
                {"Smart TVs", "Televisores inteligentes"},
                {"Periféricos", "Webcams, microfones e acessórios"},
                {"Redes", "Roteadores, switches e adaptadores de rede"},
                {"Fontes de Alimentação", "Fontes para computadores"},
                {"Gabinetes", "Gabinetes para computadores"},
                {"Refrigeração", "Coolers, water coolers e ventoinhas"},
                {"Nobreaks e Energia", "Nobreaks, estabilizadores e filtros de linha"},
                {"Cabos e Conectores", "Cabos HDMI, USB, DisplayPort e adaptadores"},
                {"Acessórios Gamer", "Controles, mousepads e acessórios gamer"},
                {"Armazenamento Externo", "Pen drives, cartões de memória e HDs externos"},
                {"Softwares", "Sistemas operacionais, licenças e programas"}
        };

        for (int i = 0; i < dados.length; i++) {

            Categoria categoria = new Categoria();

            categoria.setId((long) (i + 1));
            categoria.setNome(dados[i][0]);
            categoria.setDescricao(dados[i][1]);

            categorias.add(categoria);
        }

        return categorias;
    }

}
