Sur le principe les messages queues fonctionnent de la meme maniere que la tache précedent. A l'exception de la maniere ou l'on recupereet ecris le message. Ici on envoie et recupere par blocs. Le QueueBroker va etablir la connexion entre les deux Task et le MessageQueue va s'occuper d'envoyer et recevoir les messages.

# MessageQueue

### Envoi
void send(byte[] bytes, int offset, int length);
ecrit length éléments de l'array bytes en parametre à la position offset.
La range [offset,offset+length[ doit etre contenu dans l'array sans qu'il y ai du wrapping des deux cotés.
La fonction bloquante tant que l'entiereté des bytes n'ont pas été envoyé et donc receptionné.
Renvoie une exception si le MessageQueue est deconncté pendant l'envoi


### Reception
byte[] receive();
receptionne la totalité des bytes qui ont été envoyé par send.
La fonction est bloquante tant que l'entiéreté des bytes n'a pas été recu.
Renvoie une exception si le MessageQueue est deconncté pendant la reception

### Deconnection
void close();
A l'invocation de cette methode, l'envoi et la reception des byte n'est plus possible. Meme comportement que pour les channels.
