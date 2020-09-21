echo "Executando o curl...";

curl -X POST \
  -H "Content-Type: multipart/form-data" \
  -F "file=@music.mp3" \
  http://localhost:8085/v1/customers/76855086-d048-404d-92d7-0f837c57cd68/musics/acdbb018-6926-4967-8444-e4f6f8347ce2

echo "\n";