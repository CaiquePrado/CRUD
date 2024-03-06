package br.com.crud.domain.address.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
  
  MARANHAO("MA"),
  PIAUI("PI"),
  CEARA("CE"),
  RIO_GRANDE_DO_NORTE("RN"),
  PARAIBA("PB"),
  PERNAMBUCO("PE"),
  ALAGOAS("AL"),
  SERGIPE("SE"),
  BAHIA("BA"),
  RONDONIA("RO"),
  RORAIMA("RR"),
  AMAZONAS("AM"),
  PARA("PA"),
  AMAPA("AP"),
  TOCANTINS("TO"),
  ACRE("AC"),
  MATO_GROSSO("MT"),
  MATO_GROSSO_DO_SUL("MS"),
  GOIAS("GO"),
  DISTRITO_FEDERAL("DF"),
  MINAS_GERAIS("MG"),
  ESPIRITO_SANTO("ES"),
  RIO_DE_JANEIRO("RJ"),
  SAO_PAULO("SP"),
  PARANA("PR"),
  SANTA_CATARINA("SC"),
  RIO_GRANDE_DO_SUL("RS");

  private final String state;
}
