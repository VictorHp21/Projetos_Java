@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo ==== 1/3 ETL ====
python etl\etl.py || goto erro
echo.
echo ==== 2/3 Analise ====
python analise\analise.py || goto erro
echo.
echo ==== 3/3 Dashboard ====
python dashboard\gerar_dashboard.py || goto erro
start "" "dashboard\dashboard.html"
echo.
echo Pipeline concluido.
pause
exit /b 0
:erro
echo.
echo ERRO: veja a mensagem acima.
pause
exit /b 1
