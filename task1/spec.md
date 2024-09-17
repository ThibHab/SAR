## Broker

Broker(String name);
Constructeur de Broker.
name: Le nom du Broker qui sert à l'identifier. Deux Broker ne peuvent pas avoir le meme nom si la connexion est sur le meme port mais il est possible d'avoir deux Broker avec le meme nom sur deux ports différents


Channel accept(int port);
Accepte une connexion entrante sur un port spécifique.
port: Le port sur lequel accepter la connexion.
return: Le Channel qui sera utilisé pour la communication.

Channel connect(String name, int port);
Connecte ce Broker à un autre Broker spécifié par son nom sur un port spécifié.
name: Le nom du Broker auquel se connecter.
port: Le port sur lequel se connecter.
return: Le Channel accepté pour établir la connexion.


## Channel
Un channel a un flux bidirectionnel en FIFO lossless qui permet la connexion entre deux Task. La gestion de la connexion est géré par les Broker des deux Tasks.

int read(byte[] bytes, int offset, int length);
Lit un nombre spécifié de bytes à partir d'un offset donné.
bytes: Le tableau dans lequel stocker les données lues.
offset: L'indice à partir duquel commencer la lecture.
length: Le nombre de bytes à lire.
return: Le nombre de bytes lus ou -1 en cas d'erreur. Le cas ou le return est 0 est impossible puisque la fonction est bloquante et attend d'au moins rendre 1 byte


int write(byte[] bytes, int offset, int length);
Écrit un nombre spécifié de bytes à partir d'un offset donné.
bytes: Le tableau contenant les données à écrire.
offset: L'indice à partir duquel commencer l'écriture.
length: Le nombre de bytes à écrire.
return: Le nombre de bytes écrits ou -1 en cas d'erreur.

void disconnect();
Déconnecte le Channel.

boolean disconnected();
Vérifie si le Channel est déconnecté.
return: True si le Channel est deconnecté, False sinon
