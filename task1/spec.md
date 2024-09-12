## Broker

Broker(String name);
un Broker poossede un nom qui sert a l'identifier

Channel accept(int port);
le broker accepte la demande de connexion venant du port "port"
renvoie le channel qui va etre utilisé.

Channel connect(String name, int port);
connect le broker au broker au nom "name" sur le port "port"
renvoie le channel qui a été accepté pour établir la connexion

## Channel
FIFO lossless,flux bidirectionel

int read(byte[] bytes, int offset, int length);
permet de lire "length" byte à partir de "offset"
les données lu sont stockée dans "bytes"
on renvoie le nombre de byte lu et -1 en cas d'erreur renvoie de 0 impossible puisque la fonction est bloquante

int write(byte[] bytes, int offset, int length);
permet de lire les "length" éléments de buffer de la task a partir de "offset"
les données lu sont stockée dans "bytes"
on renvoie 0 si il n'y a pas d'erreur

void disconnect();
arrete la connexion entre les deux extremité du channel

boolean disconnected();
renvoie true si la connexion de ce channel est arrété false sinon

