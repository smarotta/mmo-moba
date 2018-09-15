@ECHO OFF
SET PWD=%cd%\
SET DST_DIR=..\..\java\
SET SRC_DIR=messages\
if not exist "%DST_DIR%" mkdir %DST_DIR%

for /r %%v in (%SRC_DIR%*.proto) do protoc --proto_path=%PWD%%SRC_DIR% --java_out=%PWD%%DST_DIR% %%v
