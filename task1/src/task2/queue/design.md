Concenrnant la task2 (Queue) on asit deja que la connexion entre Task est géré par les QueueBroker.

Contrairement à la task1 on a pas besoin de BrokerManager et RDV puisque l'on utilise la task1 comme sous cuche.
Chaque task possede un Queue broker qui a un nom.
Pour permettre l'echange le MessageQueue utilise les Channel vu lors de la task 1.

On a besoin d'avoir un message lu d'un coup.
Pour cela on envoie la taille avant d'envoyer les datas. Le recepteur recoit la taille en question et renvoie