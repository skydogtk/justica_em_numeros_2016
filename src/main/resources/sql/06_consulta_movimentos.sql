SELECT 
    pe.id_processo_evento, 
    
    /*** movimento ***/
    /* dataHora */
    to_char(pe.dt_atualizacao, 'yyyymmddhh24miss') AS dta_ocorrencia,
    /* nivelSigilo */
    case when pe.in_visibilidade_externa = true then 0 else 5 end AS in_visibilidade_externa,
    /* identificadorMovimento */
    -- pe.id_processo_evento,
    
    /*** movimentoNacional ***/
    /* codigoNacional */
    pe.id_evento AS cd_movimento_cnj,
    
    /* TRT4 */
    pe.ds_texto_final_interno
FROM tb_processo_evento pe 
INNER JOIN tb_evento ev ON (ev.id_evento = pe.id_evento AND ev.in_ativo  = 'S') 
WHERE 1=1
    AND pe.id_processo_evento_excludente IS NULL
    AND pe.id_processo = :id_processo
ORDER BY pe.dt_atualizacao ASC